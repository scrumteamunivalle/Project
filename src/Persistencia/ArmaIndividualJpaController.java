/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.ArmaIndividual;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Contenedor;
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
public class ArmaIndividualJpaController implements Serializable {

    public ArmaIndividualJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("ProjectPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ArmaIndividual armaIndividual) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contenedor contenedor = armaIndividual.getContenedor();
            if (contenedor != null) {
                contenedor = em.getReference(contenedor.getClass(), contenedor.getId());
                armaIndividual.setContenedor(contenedor);
            }
            em.persist(armaIndividual);
            if (contenedor != null) {
                contenedor.getArmaIndividualList().add(armaIndividual);
                contenedor = em.merge(contenedor);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findArmaIndividual(armaIndividual.getId()) != null) {
                throw new PreexistingEntityException("ArmaIndividual " + armaIndividual + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ArmaIndividual armaIndividual) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ArmaIndividual persistentArmaIndividual = em.find(ArmaIndividual.class, armaIndividual.getId());
            Contenedor contenedorOld = persistentArmaIndividual.getContenedor();
            Contenedor contenedorNew = armaIndividual.getContenedor();
            if (contenedorNew != null) {
                contenedorNew = em.getReference(contenedorNew.getClass(), contenedorNew.getId());
                armaIndividual.setContenedor(contenedorNew);
            }
            armaIndividual = em.merge(armaIndividual);
            if (contenedorOld != null && !contenedorOld.equals(contenedorNew)) {
                contenedorOld.getArmaIndividualList().remove(armaIndividual);
                contenedorOld = em.merge(contenedorOld);
            }
            if (contenedorNew != null && !contenedorNew.equals(contenedorOld)) {
                contenedorNew.getArmaIndividualList().add(armaIndividual);
                contenedorNew = em.merge(contenedorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = armaIndividual.getId();
                if (findArmaIndividual(id) == null) {
                    throw new NonexistentEntityException("The armaIndividual with id " + id + " no longer exists.");
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
            ArmaIndividual armaIndividual;
            try {
                armaIndividual = em.getReference(ArmaIndividual.class, id);
                armaIndividual.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The armaIndividual with id " + id + " no longer exists.", enfe);
            }
            Contenedor contenedor = armaIndividual.getContenedor();
            if (contenedor != null) {
                contenedor.getArmaIndividualList().remove(armaIndividual);
                contenedor = em.merge(contenedor);
            }
            em.remove(armaIndividual);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ArmaIndividual> findArmaIndividualEntities() {
        return findArmaIndividualEntities(true, -1, -1);
    }

    public List<ArmaIndividual> findArmaIndividualEntities(int maxResults, int firstResult) {
        return findArmaIndividualEntities(false, maxResults, firstResult);
    }

    private List<ArmaIndividual> findArmaIndividualEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ArmaIndividual.class));
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

    public ArmaIndividual findArmaIndividual(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ArmaIndividual.class, id);
        } finally {
            em.close();
        }
    }

    public int getArmaIndividualCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ArmaIndividual> rt = cq.from(ArmaIndividual.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
