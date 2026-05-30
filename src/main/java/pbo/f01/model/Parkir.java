/**
 * Nama: Davina Olivia Yosefanny Hutabarat
 * NIM: 12S24047
 */

package pbo.f01.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "parkir")
public class Parkir {

    @Id
    @Column(name = "nama", length = 100, nullable = false)
    private String nama;

    @Column(name = "kapasitas", nullable = false)
    private int kapasitas;

    @Column(name = "allowed", nullable = false)
    private String allowed;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "PAR_KEN",
        joinColumns = @JoinColumn(name = "PAR_NAMA", referencedColumnName = "nama"),
        inverseJoinColumns = @JoinColumn(name = "KEN_PLATE", referencedColumnName = "plate")
    )
    private List<Kendaraan> kendaraanList = new ArrayList<>();

    public Parkir() {
    }

    public Parkir(String nama, int kapasitas, String allowed) {
        this.nama = nama;
        this.kapasitas = kapasitas;
        this.allowed = allowed;
    }

    // Getter
    public String getNama() {
        return nama;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public String getAllowed() {
        return allowed;
    }

    public List<Kendaraan> getKendaraanList() {
        return kendaraanList;
    }

    // Setter
    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    public void setAllowed(String allowed) {
        this.allowed = allowed;
    }

    public void setKendaraanList(List<Kendaraan> kendaraanList) {
        this.kendaraanList = kendaraanList;
    }

    @Override
    public String toString() {
        int filled = (kendaraanList != null) ? kendaraanList.size() : 0;
        return nama + " " + allowed + " " + kapasitas + "|" + filled;
    }
}
