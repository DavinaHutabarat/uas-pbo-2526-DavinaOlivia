/**
 * Nama: Davina Olivia Yosefanny Hutabarat
 * NIM: 12S24047
 */

package pbo.f01;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pbo.f01.model.Kendaraan;
import pbo.f01.model.Parkir;

/**
 * Driver class utama
 * Nama: Davina Olivia Yosefanny Hutabarat
 * NIM: 12S24047
 */
public class App {

    private static EntityManagerFactory factory;
    private static EntityManager entityManager;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        factory = Persistence.createEntityManagerFactory("parkit-pu");
        entityManager = factory.createEntityManager();

        // Membaca input baris per baris hingga tidak ada token atau teks lagi
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            if (input == null || input.trim().isEmpty()) {
                break;
            }

            String[] strTemp = input.split("#");
            switch (strTemp[0].trim()) {

                case "area-add": {
                    // Format: area-add#<name>#<capacity>#<allowed_type>
                    String tempNama = strTemp[1];
                    int tempKapasitas = Integer.parseInt(strTemp[2]);
                    String tempAllowed = strTemp[3];

                    entityManager.getTransaction().begin();
                    Parkir existing = entityManager.find(Parkir.class, tempNama);
                    if (existing == null) {
                        Parkir parkir = new Parkir(tempNama, tempKapasitas, tempAllowed);
                        entityManager.persist(parkir);
                    }
                    entityManager.getTransaction().commit();
                    break;
                }

                case "vehicle-add": {
                    // Format: vehicle-add#<plate_number>#<owner>#<type>
                    String tempPlate = strTemp[1];
                    String tempOwner = strTemp[2];
                    String tempType = strTemp[3];

                    entityManager.getTransaction().begin();
                    Kendaraan existing = entityManager.find(Kendaraan.class, tempPlate);
                    if (existing == null) {
                        Kendaraan kendaraan = new Kendaraan(tempPlate, tempOwner, tempType);
                        entityManager.persist(kendaraan);
                    }
                    entityManager.getTransaction().commit();
                    break;
                }

                case "park": {
                    // Format: park#<plate_number>#<area_name>
                    String tempPlate = strTemp[1];
                    String tempArea = strTemp[2];

                    entityManager.getTransaction().begin();
                    Kendaraan kendaraan = entityManager.find(Kendaraan.class, tempPlate);
                    Parkir parkir = entityManager.find(Parkir.class, tempArea);

                    if (kendaraan != null && parkir != null) {
                        // Validasi tipe kendaraan sesuai allowed_type area
                        boolean typeMatch = kendaraan.getType().equals(parkir.getAllowed());
                        // Validasi kapasitas tidak melebihi
                        boolean hasCapacity = parkir.getKendaraanList().size() < parkir.getKapasitas();
                        // Validasi kendaraan belum ada di area ini
                        boolean notDuplicate = !parkir.getKendaraanList().contains(kendaraan);

                        if (typeMatch && hasCapacity && notDuplicate) {
                            parkir.getKendaraanList().add(kendaraan);
                            entityManager.merge(parkir);
                        }
                    }
                    entityManager.getTransaction().commit();
                    break;
                }

                case "display-all": {
                    // Tampilkan semua area parkir (ascending by name) dan kendaraan di dalamnya (ascending by plate)
                    List<Parkir> allParkir = entityManager
                        .createQuery("SELECT p FROM Parkir p ORDER BY p.nama ASC", Parkir.class)
                        .getResultList();

                    for (Parkir parkir : allParkir) {
                        System.out.println(parkir.toString());
                        List<Kendaraan> sorted = parkir.getKendaraanList().stream()
                            .sorted(Comparator.comparing(Kendaraan::getPlate))
                            .collect(Collectors.toList());
                        for (Kendaraan k : sorted) {
                            System.out.println(k.toString());
                        }
                    }
                    break;
                }

                default:
                    // Input tidak dikenal, abaikan
                    break;
            }
        }

        scanner.close();
        entityManager.close();
        factory.close();
    }
}
