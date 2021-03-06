/*
 * The MIT License
 *
 * Copyright 2017 Miller.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package co.edu.uniandes.csw.artesanias.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Entidad de las boletas.
 *
 * @author ma.trujillo10
 */
@Entity
public class BoletaEntity implements Serializable {

    //--------------------------------------------------------------------------
    // Constantes
    //--------------------------------------------------------------------------
    /**
     * Descuento para menores de edad.
     */
    public final static int MENORES = 0;

    /**
     * Precio regular de la boleta.
     */
    public final static int REGULAR = 1;

    /**
     * Descuento para mayores de 65 años.
     */
    public final static int MAYORES = 2;

    //--------------------------------------------------------------------------
    // Atributos
    //--------------------------------------------------------------------------
    /**
     * Id de la boleta. Valor auto generado por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Tipo de boleta.
     */
    @Column(nullable = false)
    private Integer tipo;

    /**
     * Fecha de inicio de la boleta.
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(nullable = false)
    private Date inicio;

    /**
     * Fecha de fin de la boleta.
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(nullable = false)
    private Date fin;

    /**
     * Fecha de la boleta.
     */
    @Column(nullable = false)
    private Double precio;

    /**
     * 4
     * Feria de la boleta.
     */
    @ManyToOne(targetEntity = FeriaEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_feria")
    @PodamExclude
    private FeriaEntity feria;

    /**
     * Dueño de la boleta.
     */
    @ManyToOne(targetEntity = EspectadorEntity.class, fetch = FetchType.LAZY)
    @PodamExclude
    private EspectadorEntity espectador;

    //--------------------------------------------------------------------------
    // Métodos
    //--------------------------------------------------------------------------
    /**
     * Devuelve el id de la boleta.
     *
     * @return id de la bolet.
     */
    public Long getId() {
        return id;
    }

    /**
     * Cambia el id de la boleta. post: Se cambio el id de la boleta.
     *
     * @param id nuevo id de la boleta.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Devuelve el tipo de la boleta.
     *
     * @return tipo de la boleta.
     */
    public Integer getTipo() {
        return tipo;
    }

    /**
     * Cambia el tipo de la boleta. post: Se cambio el tipo de la boleta.
     *
     * @param tipo nuevo tipo de la boleta.
     */
    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    /**
     * Devuelve la fecha de inicio de la boleta.
     *
     * @return fecha de inicio de la boleta.
     */
    public Date getInicio() {
        return inicio;
    }

    /**
     * Cambia la fecha de inicio de la boleta. post: Se cambio la fecha de
     * inicio de la boleta.
     *
     * @param inicio nueva fecha de inicio de la boleta.
     */
    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    /**
     * Devuelve la fecha de fin de la boleta.
     *
     * @return fecha de inicio de la boleta.
     */
    public Date getFin() {
        return fin;
    }

    /**
     * Cambia la fecha de fin de la boleta. post: Se cambio la fecha de fin de
     * la boleta.
     *
     * @param fin nueva fecha de fin de la boleta.
     */
    public void setFin(Date fin) {
        this.fin = fin;
    }

    /**
     * Devuelve el precio de la boleta.
     *
     * @return precio de la boleta.
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * Cambia el precio de la boleta. post: Se cambio el precio de la boleta.
     *
     * @param precio nuevo precio de la boleta.
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * Devuelve la feria de la boleta.
     *
     * @return feria de la boleta.
     */
    public FeriaEntity getFeria() {
        return feria;
    }

    /**
     * Cambia la feria de la boleta. post: Se cambio la feria de la boleta.
     *
     * @param feria nueva feria de la boleta.
     */
    public void setFeria(FeriaEntity feria) {
        this.feria = feria;
    }

    /**
     * Devuelve el dueño de la boleta.
     *
     * @return dueño de la boleta.
     */
    public EspectadorEntity getEspectador() {
        return espectador;
    }

    /**
     * Cambia la dueño de la boleta. post: Se cambio el dueño de la boleta.
     *
     * @param espectador nuevo dueño de la boleta.
     */
    public void setEspectador(EspectadorEntity espectador) {
        this.espectador = espectador;
    }

    //--------------------------------------------------------------------------
    // Métodos del objeto
    //--------------------------------------------------------------------------
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BoletaEntity other = (BoletaEntity) obj;
        return Objects.equals(this.id, other.id);
    }
}
