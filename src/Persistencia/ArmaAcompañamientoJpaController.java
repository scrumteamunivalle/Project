/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.ArmaAcompañamiento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Bloque;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author GekkoHKD
 */
public class ArmaAcompañamientoJpaController implements Serializable {

    public ArmaAcompañamientoJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("ProjectPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ArmaAcompañamiento armaAcompañamiento) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bloque bloque = armaAcompañamiento.getBloque();
            if (bloque != null) {
                bloque = em.getReference(bloque.getClass(), bloque.getId());
                armaAcompañamiento.setBloque(bloque);
            }
            em.persist(armaAcompañamiento);
            if (bloque != null) {
                bloque.getArmaAcompañamientoList().add(armaAcompañamiento);
                bloque = em.merge(bloque);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findArmaAcompañamiento(armaAcompañamiento.getId()) != null) {
                throw new PreexistingEntityException("ArmaAcompa\u00f1amiento " + armaAcompañamiento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ArmaAcompañamiento armaAcompañamiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ArmaAcompañamiento persistentArmaAcompañamiento = em.find(ArmaAcompañamiento.class, armaAcompañamiento.getId());
            Bloque bloqueOld = persistentArmaAcompañamiento.getBloque();
            Bloque bloqueNew = armaAcompañamiento.getBloque();
            if (bloqueNew != null) {
                bloqueNew = em.getReference(bloqueNew.getClass(), bloqueNew.getId());
                armaAcompañamiento.setBloque(bloqueNew);
            }
            armaAcompañamiento = em.merge(armaAcompañamiento);
            if (bloqueOld != null && !bloqueOld.equals(bloqueNew)) {
                bloqueOld.getArmaAcompañamientoList().remove(armaAcompañamiento);
                bloqueOld = em.merge(bloqueOld);
            }
            if (bloqueNew != null && !bloqueNew.equals(bloqueOld)) {
                bloqueNew.getArmaAcompañamientoList().add(armaAcompañamiento);
                bloqueNew = em.merge(bloqueNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = armaAcompañamiento.getId();
                if (findArmaAcompañamiento(id) == null) {
                    throw new NonexistentEntityException("The armaAcompa\u00f1amiento with id " + id + " no longer exists.");
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
            ArmaAcompañamiento armaAcompañamiento;
            try {
                armaAcompañamiento = em.getReference(ArmaAcompañamiento.class, id);
                armaAcompañamiento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The armaAcompa\u00f1amiento with id " + id + " no longer exists.", enfe);
            }
            Bloque bloque = armaAcompañamiento.getBloque();
            if (bloque != null) {
                bloque.getArmaAcompañamientoList().remove(armaAcompañamiento);
                bloque = em.merge(bloque);
            }
            em.remove(armaAcompañamiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ArmaAcompañamiento> findArmaAcompañamientoEntities() {
        return findArmaAcompañamientoEntities(true, -1, -1);
    }

    public List<ArmaAcompañamiento> findArmaAcompañamientoEntities(int maxResults, int firstResult) {
        return findArmaAcompañamientoEntities(false, maxResults, firstResult);
    }

    private List<ArmaAcompañamiento> findArmaAcompañamientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ArmaAcompañamiento.class));
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

    public ArmaAcompañamiento findArmaAcompañamiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ArmaAcompañamiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getArmaAcompañamientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ArmaAcompañamiento> rt = cq.from(ArmaAcompañamiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
