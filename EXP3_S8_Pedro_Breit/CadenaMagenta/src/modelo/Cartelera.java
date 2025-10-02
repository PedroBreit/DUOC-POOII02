package modelo;

/**
 * Clase Cartelera.
 * Representa la entidad con la info de una película en cartelera.
 */
public class Cartelera {
    private Integer id;
    private String titulo;
    private String director;
    private String anio;
    private String duracion;
    private String genero;

    public Cartelera() {}

    public Cartelera(Integer id, String titulo, String director, String anio, String duracion, String genero) {
        this.id = id; this.titulo = titulo; this.director = director;
        this.anio = anio; this.duracion = duracion; this.genero = genero;
    }

    // Getters / Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }
    public String getAnio() { return anio; }
    public void setAnio(String anio) { this.anio = anio; }
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    @Override public String toString() {
        return "Cartelera{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", director='" + director + '\'' +
                ", año=" + anio +
                ", duracion=" + duracion +
                ", genero='" + genero + '\'' +
                '}';
    }
}

