/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.IntegranteFarcep;
import Modelo.Pqr;
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
public class PqrJpaController implements Serializable {

    public PqrJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("ProjectPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pqr pqr) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IntegranteFarcep cedula = pqr.getCedula();
            if (cedula != null) {
                cedula = em.getReference(cedula.getClass(), cedula.getCedula());
                pqr.setCedula(cedula);
            }
            em.persist(pqr);
            if (cedula != null) {
                cedula.getPqrList().add(pqr);
                cedula = em.merge(cedula);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPqr(pqr.getId()) != null) {
                throw new PreexistingEntityException("Pqr " + pqr + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pqr pqr) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pqr persistentPqr = em.find(Pqr.class, pqr.getId());
            IntegranteFarcep cedulaOld = persistentPqr.getCedula();
            IntegranteFarcep cedulaNew = pqr.getCedula();
            if (cedulaNew != null) {
                cedulaNew = em.getReference(cedulaNew.getClass(), cedulaNew.getCedula());
                pqr.setCedula(cedulaNew);
            }
            pqr = em.merge(pqr);
            if (cedulaOld != null && !cedulaOld.equals(cedulaNew)) {
                cedulaOld.getPqrList().remove(pqr);
                cedulaOld = em.merge(cedulaOld);
            }
            if (cedulaNew != null && !cedulaNew.equals(cedulaOld)) {
                cedulaNew.getPqrList().add(pqr);
                cedulaNew = em.merge(cedulaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pqr.getId();
                if (findPqr(id) == null) {
                    throw new NonexistentEntityException("The pqr with id " + id + " no longer exists.");
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
            Pqr pqr;
            try {
                pqr = em.getReference(Pqr.class, id);
                pqr.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pqr with id " + id + " no longer exists.", enfe);
            }
            IntegranteFarcep cedula = pqr.getCedula();
            if (cedula != null) {
                cedula.getPqrList().remove(pqr);
                cedula = em.merge(cedula);
            }
            em.remove(pqr);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pqr> findPqrEntities() {
        return findPqrEntities(true, -1, -1);
    }

    public List<Pqr> findPqrEntities(int maxResults, int firstResult) {
        return findPqrEntities(false, maxResults, firstResult);
    }

    private List<Pqr> findPqrEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pqr.class));
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

    public Pqr findPqr(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pqr.class, id);
        } finally {
            em.close();
        }
    }

    public int getPqrCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pqr> rt = cq.from(Pqr.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
