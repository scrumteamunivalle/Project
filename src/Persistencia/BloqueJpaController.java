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
import Modelo.ArmaAcompañamiento;
import Modelo.Bloque;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author GekkoHKD
 */
public class BloqueJpaController implements Serializable {

    public BloqueJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("ProjectPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bloque bloque) throws PreexistingEntityException, Exception {
        if (bloque.getArmaAcompañamientoList() == null) {
            bloque.setArmaAcompañamientoList(new ArrayList<ArmaAcompañamiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ArmaAcompañamiento> attachedArmaAcompañamientoList = new ArrayList<ArmaAcompañamiento>();
            for (ArmaAcompañamiento armaAcompañamientoListArmaAcompañamientoToAttach : bloque.getArmaAcompañamientoList()) {
                armaAcompañamientoListArmaAcompañamientoToAttach = em.getReference(armaAcompañamientoListArmaAcompañamientoToAttach.getClass(), armaAcompañamientoListArmaAcompañamientoToAttach.getId());
                attachedArmaAcompañamientoList.add(armaAcompañamientoListArmaAcompañamientoToAttach);
            }
            bloque.setArmaAcompañamientoList(attachedArmaAcompañamientoList);
            em.persist(bloque);
            for (ArmaAcompañamiento armaAcompañamientoListArmaAcompañamiento : bloque.getArmaAcompañamientoList()) {
                Bloque oldBloqueOfArmaAcompañamientoListArmaAcompañamiento = armaAcompañamientoListArmaAcompañamiento.getBloque();
                armaAcompañamientoListArmaAcompañamiento.setBloque(bloque);
                armaAcompañamientoListArmaAcompañamiento = em.merge(armaAcompañamientoListArmaAcompañamiento);
                if (oldBloqueOfArmaAcompañamientoListArmaAcompañamiento != null) {
                    oldBloqueOfArmaAcompañamientoListArmaAcompañamiento.getArmaAcompañamientoList().remove(armaAcompañamientoListArmaAcompañamiento);
                    oldBloqueOfArmaAcompañamientoListArmaAcompañamiento = em.merge(oldBloqueOfArmaAcompañamientoListArmaAcompañamiento);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBloque(bloque.getId()) != null) {
                throw new PreexistingEntityException("Bloque " + bloque + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bloque bloque) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bloque persistentBloque = em.find(Bloque.class, bloque.getId());
            List<ArmaAcompañamiento> armaAcompañamientoListOld = persistentBloque.getArmaAcompañamientoList();
            List<ArmaAcompañamiento> armaAcompañamientoListNew = bloque.getArmaAcompañamientoList();
            List<String> illegalOrphanMessages = null;
            for (ArmaAcompañamiento armaAcompañamientoListOldArmaAcompañamiento : armaAcompañamientoListOld) {
                if (!armaAcompañamientoListNew.contains(armaAcompañamientoListOldArmaAcompañamiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ArmaAcompa\u00f1amiento " + armaAcompañamientoListOldArmaAcompañamiento + " since its bloque field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ArmaAcompañamiento> attachedArmaAcompañamientoListNew = new ArrayList<ArmaAcompañamiento>();
            for (ArmaAcompañamiento armaAcompañamientoListNewArmaAcompañamientoToAttach : armaAcompañamientoListNew) {
                armaAcompañamientoListNewArmaAcompañamientoToAttach = em.getReference(armaAcompañamientoListNewArmaAcompañamientoToAttach.getClass(), armaAcompañamientoListNewArmaAcompañamientoToAttach.getId());
                attachedArmaAcompañamientoListNew.add(armaAcompañamientoListNewArmaAcompañamientoToAttach);
            }
            armaAcompañamientoListNew = attachedArmaAcompañamientoListNew;
            bloque.setArmaAcompañamientoList(armaAcompañamientoListNew);
            bloque = em.merge(bloque);
            for (ArmaAcompañamiento armaAcompañamientoListNewArmaAcompañamiento : armaAcompañamientoListNew) {
                if (!armaAcompañamientoListOld.contains(armaAcompañamientoListNewArmaAcompañamiento)) {
                    Bloque oldBloqueOfArmaAcompañamientoListNewArmaAcompañamiento = armaAcompañamientoListNewArmaAcompañamiento.getBloque();
                    armaAcompañamientoListNewArmaAcompañamiento.setBloque(bloque);
                    armaAcompañamientoListNewArmaAcompañamiento = em.merge(armaAcompañamientoListNewArmaAcompañamiento);
                    if (oldBloqueOfArmaAcompañamientoListNewArmaAcompañamiento != null && !oldBloqueOfArmaAcompañamientoListNewArmaAcompañamiento.equals(bloque)) {
                        oldBloqueOfArmaAcompañamientoListNewArmaAcompañamiento.getArmaAcompañamientoList().remove(armaAcompañamientoListNewArmaAcompañamiento);
                        oldBloqueOfArmaAcompañamientoListNewArmaAcompañamiento = em.merge(oldBloqueOfArmaAcompañamientoListNewArmaAcompañamiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bloque.getId();
                if (findBloque(id) == null) {
                    throw new NonexistentEntityException("The bloque with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bloque bloque;
            try {
                bloque = em.getReference(Bloque.class, id);
                bloque.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bloque with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ArmaAcompañamiento> armaAcompañamientoListOrphanCheck = bloque.getArmaAcompañamientoList();
            for (ArmaAcompañamiento armaAcompañamientoListOrphanCheckArmaAcompañamiento : armaAcompañamientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Bloque (" + bloque + ") cannot be destroyed since the ArmaAcompa\u00f1amiento " + armaAcompañamientoListOrphanCheckArmaAcompañamiento + " in its armaAcompa\u00f1amientoList field has a non-nullable bloque field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(bloque);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bloque> findBloqueEntities() {
        return findBloqueEntities(true, -1, -1);
    }

    public List<Bloque> findBloqueEntities(int maxResults, int firstResult) {
        return findBloqueEntities(false, maxResults, firstResult);
    }

    private List<Bloque> findBloqueEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bloque.class));
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

    public Bloque findBloque(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bloque.class, id);
        } finally {
            em.close();
        }
    }

    public int getBloqueCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bloque> rt = cq.from(Bloque.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
