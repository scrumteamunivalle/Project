/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Actividades;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author GekkoHKD
 */
public class ActividadesJpaController implements Serializable {

    public ActividadesJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("ProjectPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Actividades actividades) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(actividades);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findActividades(actividades.getCódigo()) != null) {
                throw new PreexistingEntityException("Actividades " + actividades + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Actividades actividades) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            actividades = em.merge(actividades);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = actividades.getCódigo();
                if (findActividades(id) == null) {
                    throw new NonexistentEntityException("The actividades with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actividades actividades;
            try {
                actividades = em.getReference(Actividades.class, id);
                actividades.getCódigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actividades with id " + id + " no longer exists.", enfe);
            }
            em.remove(actividades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Actividades> findActividadesEntities() {
        return findActividadesEntities(true, -1, -1);
    }

    public List<Actividades> findActividadesEntities(int maxResults, int firstResult) {
        return findActividadesEntities(false, maxResults, firstResult);
    }

    private List<Actividades> findActividadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Actividades.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Actividades findActividades(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Actividades.class, id);
        } finally {
            em.close();
        }
    }

    public int getActividadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Actividades> rt = cq.from(Actividades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
