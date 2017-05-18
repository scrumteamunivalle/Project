/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.Civil;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.ZonaVeredal;
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
public class CivilJpaController implements Serializable {

    public CivilJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("ProjectPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Civil civil) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ZonaVeredal zona = civil.getZona();
            if (zona != null) {
                zona = em.getReference(zona.getClass(), zona.getId());
                civil.setZona(zona);
            }
            em.persist(civil);
            if (zona != null) {
                zona.getCivilList().add(civil);
                zona = em.merge(zona);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCivil(civil.getCedula()) != null) {
                throw new PreexistingEntityException("Civil " + civil + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Civil civil) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Civil persistentCivil = em.find(Civil.class, civil.getCedula());
            ZonaVeredal zonaOld = persistentCivil.getZona();
            ZonaVeredal zonaNew = civil.getZona();
            if (zonaNew != null) {
                zonaNew = em.getReference(zonaNew.getClass(), zonaNew.getId());
                civil.setZona(zonaNew);
            }
            civil = em.merge(civil);
            if (zonaOld != null && !zonaOld.equals(zonaNew)) {
                zonaOld.getCivilList().remove(civil);
                zonaOld = em.merge(zonaOld);
            }
            if (zonaNew != null && !zonaNew.equals(zonaOld)) {
                zonaNew.getCivilList().add(civil);
                zonaNew = em.merge(zonaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = civil.getCedula();
                if (findCivil(id) == null) {
                    throw new NonexistentEntityException("The civil with id " + id + " no longer exists.");
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
            Civil civil;
            try {
                civil = em.getReference(Civil.class, id);
                civil.getCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The civil with id " + id + " no longer exists.", enfe);
            }
            ZonaVeredal zona = civil.getZona();
            if (zona != null) {
                zona.getCivilList().remove(civil);
                zona = em.merge(zona);
            }
            em.remove(civil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Civil> findCivilEntities() {
        return findCivilEntities(true, -1, -1);
    }

    public List<Civil> findCivilEntities(int maxResults, int firstResult) {
        return findCivilEntities(false, maxResults, firstResult);
    }

    private List<Civil> findCivilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Civil.class));
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

    public Civil findCivil(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Civil.class, id);
        } finally {
            em.close();
        }
    }

    public int getCivilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Civil> rt = cq.from(Civil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
