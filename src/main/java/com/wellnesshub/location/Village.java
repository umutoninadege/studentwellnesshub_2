package com.wellnesshub.location;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "villages")
public class Village {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 20)
    @NotBlank
    @Size(min = 2, max = 20)
    private String code;
    
    @Column(nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cell_id", nullable = false)
    private Cell cell;
    
    // Constructors
    public Village() {}
    
    public Village(String code, String name, Cell cell) {
        this.code = code;
        this.name = name;
        this.cell = cell;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Cell getCell() { return cell; }
    public void setCell(Cell cell) { this.cell = cell; }
}
