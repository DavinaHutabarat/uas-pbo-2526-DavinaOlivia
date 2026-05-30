/**
 * Nama: Davina Olivia Yosefanny Hutabarat
 * NIM: 12S24047
 */

package pbo.f01.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "kendaraan")
public class Kendaraan {

    @Id
    @Column(name = "plate", length = 20, nullable = false)
    private String plate;

    @Column(name = "owner", length = 100, nullable = false)
    private String owner;

    @Column(name = "type", nullable = false)
    private String type;

    @ManyToMany(mappedBy = "kendaraanList")
    private List<Parkir> parkirList;

    public Kendaraan() {
    }

    public Kendaraan(String plate, String owner, String type) {
        this.plate = plate;
        this.owner = owner;
        this.type = type;
    }

    // Getter
    public String getPlate() {
        return plate;
    }

    public String getOwner() {
        return owner;
    }

    public String getType() {
        return type;
    }

    public List<Parkir> getParkirList() {
        return parkirList;
    }

    // Setter
    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setParkirList(List<Parkir> parkirList) {
        this.parkirList = parkirList;
    }

    @Override
    public String toString() {
        return plate + " " + owner + " " + type;
    }
}
