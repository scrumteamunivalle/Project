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
import Modelo.Contenedor;
import Modelo.Explosivo;
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
public class ExplosivoJpaController implements Serializable {

    public ExplosivoJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("ProjectPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Explosivo explosivo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contenedor contenedor = explosivo.getContenedor();
            if (contenedor != null) {
                contenedor = em.getReference(contenedor.getClass(), contenedor.getId());
                explosivo.setContenedor(contenedor);
            }
            em.persist(explosivo);
            if (contenedor != null) {
                contenedor.getExplosivoList().add(explosivo);
                contenedor = em.merge(contenedor);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findExplosivo(explosivo.getId()) != null) {
                throw new PreexistingEntityException("Explosivo " + explosivo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Explosivo explosivo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Explosivo persistentExplosivo = em.find(Explosivo.class, explosivo.getId());
            Contenedor contenedorOld = persistentExplosivo.getContenedor();
            Contenedor contenedorNew = explosivo.getContenedor();
            if (contenedorNew != null) {
                contenedorNew = em.getReference(contenedorNew.getClass(), contenedorNew.getId());
                explosivo.setContenedor(contenedorNew);
            }
            explosivo = em.merge(explosivo);
            if (contenedorOld != null && !contenedorOld.equals(contenedorNew)) {
                contenedorOld.getExplosivoList().remove(explosivo);
                contenedorOld = em.merge(contenedorOld);
            }
            if (contenedorNew != null && !contenedorNew.equals(contenedorOld)) {
                contenedorNew.getExplosivoList().add(explosivo);
                contenedorNew = em.merge(contenedorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = explosivo.getId();
                if (findExplosivo(id) == null) {
                    throw new NonexistentEntityException("The explosivo with id " + id + " no longer exists.");
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
            Explosivo explosivo;
            try {
                explosivo = em.getReference(Explosivo.class, id);
                explosivo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The explosivo with id " + id + " no longer exists.", enfe);
            }
            Contenedor contenedor = explosivo.getContenedor();
            if (contenedor != null) {
                contenedor.getExplosivoList().remove(explosivo);
                contenedor = em.merge(contenedor);
            }
            em.remove(explosivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Explosivo> findExplosivoEntities() {
        return findExplosivoEntities(true, -1, -1);
    }

    public List<Explosivo> findExplosivoEntities(int maxResults, int firstResult) {
        return findExplosivoEntities(false, maxResults, firstResult);
    }

    private List<Explosivo> findExplosivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Explosivo.class));
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

    public Explosivo findExplosivo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Explosivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getExplosivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Explosivo> rt = cq.from(Explosivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
