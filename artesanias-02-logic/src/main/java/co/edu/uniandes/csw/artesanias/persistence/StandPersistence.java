/*
 * The MIT License
 *
 * Copyright 2017 ja.espinosa12.
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
package co.edu.uniandes.csw.artesanias.persistence;

import co.edu.uniandes.csw.artesanias.entities.StandEntity;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * @author ja.espinosa12
 */
@Stateless
public class StandPersistence
{
    /**
     * Entity Manager encargado de la persistencia de Entities
     */
	@PersistenceContext( unitName = "artesaniasPU" )
	protected EntityManager em;
	
        /**
         * Retorna el StandEntity con el id ingresado
         * @param id
         * @return StandEntity
         */
	public StandEntity find( Long pabellonId, Long id )
	{
		TypedQuery<StandEntity> q = em.createQuery( "SELECT R FROM StandEntity R WHERE R.id = :id AND R.pabellon.id = :pabellonId", StandEntity.class );
		q.setParameter( "id", id );
		q.setParameter( "pabellonId", pabellonId );
		List<StandEntity> res = q.getResultList( );
		return res.size( ) > 0 ? res.get( 0 ) : null;
	}
	
        /**
         * Retorna una lista con todos los StandEntity
         * @return stands
         */
	public List<StandEntity> findAll( )
	{
		TypedQuery<StandEntity> q = em.createQuery( "select u from StandEntity u", StandEntity.class );
		List<StandEntity> stands = q.getResultList( );
		return stands;
	}
	
        /**
         * Retorna una lista con todos los StandEntity en el pabellon
         * @param pabellonId
         * @return lista de StandEntity
         */
	public List<StandEntity> findAllFromPabellon( Long pabellonId )
	{
		TypedQuery<StandEntity> q = em.createQuery( "SELECT S FROM StandEntity S WHERE S.pabellon.id = :pabellonId", StandEntity.class );
		q.setParameter( "pabellonId", pabellonId );
		return q.getResultList( );
	}
	
        /**
         * Genera un nuevo StandEntity
         * @param entity
         * @return entity
         */
	public StandEntity create( StandEntity entity )
	{
		em.persist( entity );
		return entity;
	}
	
        /**
         * Actualiza el StandEntity ingresado
         * @param entity
         * @return StandEntity
         */
	public StandEntity update( StandEntity entity )
	{
		return em.merge( entity );
	}
	
        /**
         * Elimina el StandEntity con id ingresado
         * @param id 
         */
	public void delete( Long id )
	{
		StandEntity entity = em.find( StandEntity.class, id );
		em.remove( entity );
	}
}