package com.wellnesshub.location;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cells")
public class Cell {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 20)
    @NotBlank
    @Size(min = 2, max = 20)
    private String code; // Set length to 20 to match validation
    
    @Column(nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;
    
    @OneToMany(mappedBy = "cell", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Village> villages = new ArrayList<>();
    
    // Constructors
    public Cell() {}
    
    public Cell(String code, String name, Sector sector) {
        this.code = code;
        this.name = name;
        this.sector = sector;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Sector getSector() { return sector; }
    public void setSector(Sector sector) { this.sector = sector; }
    
    public List<Village> getVillages() { return villages; }
    public void setVillages(List<Village> villages) { this.villages = villages; }
    
    // Helper methods
    public void addVillage(Village village) {
        villages.add(village);
        village.setCell(this);
    }
    
    public void removeVillage(Village village) {
        villages.remove(village);
        village.setCell(null);
    }
}
