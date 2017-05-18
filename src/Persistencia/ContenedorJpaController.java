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
import Modelo.ArmaIndividual;
import Modelo.Contenedor;
import java.util.ArrayList;
import java.util.List;
import Modelo.Explosivo;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author GekkoHKD
 */
public class ContenedorJpaController implements Serializable {

    public ContenedorJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("ProjectPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contenedor contenedor) throws PreexistingEntityException, Exception {
        if (contenedor.getArmaIndividualList() == null) {
            contenedor.setArmaIndividualList(new ArrayList<ArmaIndividual>());
        }
        if (contenedor.getExplosivoList() == null) {
            contenedor.setExplosivoList(new ArrayList<Explosivo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ArmaIndividual> attachedArmaIndividualList = new ArrayList<ArmaIndividual>();
            for (ArmaIndividual armaIndividualListArmaIndividualToAttach : contenedor.getArmaIndividualList()) {
                armaIndividualListArmaIndividualToAttach = em.getReference(armaIndividualListArmaIndividualToAttach.getClass(), armaIndividualListArmaIndividualToAttach.getId());
                attachedArmaIndividualList.add(armaIndividualListArmaIndividualToAttach);
            }
            contenedor.setArmaIndividualList(attachedArmaIndividualList);
            List<Explosivo> attachedExplosivoList = new ArrayList<Explosivo>();
            for (Explosivo explosivoListExplosivoToAttach : contenedor.getExplosivoList()) {
                explosivoListExplosivoToAttach = em.getReference(explosivoListExplosivoToAttach.getClass(), explosivoListExplosivoToAttach.getId());
                attachedExplosivoList.add(explosivoListExplosivoToAttach);
            }
            contenedor.setExplosivoList(attachedExplosivoList);
            em.persist(contenedor);
            for (ArmaIndividual armaIndividualListArmaIndividual : contenedor.getArmaIndividualList()) {
                Contenedor oldContenedorOfArmaIndividualListArmaIndividual = armaIndividualListArmaIndividual.getContenedor();
                armaIndividualListArmaIndividual.setContenedor(contenedor);
                armaIndividualListArmaIndividual = em.merge(armaIndividualListArmaIndividual);
                if (oldContenedorOfArmaIndividualListArmaIndividual != null) {
                    oldContenedorOfArmaIndividualListArmaIndividual.getArmaIndividualList().remove(armaIndividualListArmaIndividual);
                    oldContenedorOfArmaIndividualListArmaIndividual = em.merge(oldContenedorOfArmaIndividualListArmaIndividual);
                }
            }
            for (Explosivo explosivoListExplosivo : contenedor.getExplosivoList()) {
                Contenedor oldContenedorOfExplosivoListExplosivo = explosivoListExplosivo.getContenedor();
                explosivoListExplosivo.setContenedor(contenedor);
                explosivoListExplosivo = em.merge(explosivoListExplosivo);
                if (oldContenedorOfExplosivoListExplosivo != null) {
                    oldContenedorOfExplosivoListExplosivo.getExplosivoList().remove(explosivoListExplosivo);
                    oldContenedorOfExplosivoListExplosivo = em.merge(oldContenedorOfExplosivoListExplosivo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContenedor(contenedor.getId()) != null) {
                throw new PreexistingEntityException("Contenedor " + contenedor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contenedor contenedor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contenedor persistentContenedor = em.find(Contenedor.class, contenedor.getId());
            List<ArmaIndividual> armaIndividualListOld = persistentContenedor.getArmaIndividualList();
            List<ArmaIndividual> armaIndividualListNew = contenedor.getArmaIndividualList();
            List<Explosivo> explosivoListOld = persistentContenedor.getExplosivoList();
            List<Explosivo> explosivoListNew = contenedor.getExplosivoList();
            List<String> illegalOrphanMessages = null;
            for (ArmaIndividual armaIndividualListOldArmaIndividual : armaIndividualListOld) {
                if (!armaIndividualListNew.contains(armaIndividualListOldArmaIndividual)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ArmaIndividual " + armaIndividualListOldArmaIndividual + " since its contenedor field is not nullable.");
                }
            }
            for (Explosivo explosivoListOldExplosivo : explosivoListOld) {
                if (!explosivoListNew.contains(explosivoListOldExplosivo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Explosivo " + explosivoListOldExplosivo + " since its contenedor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ArmaIndividual> attachedArmaIndividualListNew = new ArrayList<ArmaIndividual>();
            for (ArmaIndividual armaIndividualListNewArmaIndividualToAttach : armaIndividualListNew) {
                armaIndividualListNewArmaIndividualToAttach = em.getReference(armaIndividualListNewArmaIndividualToAttach.getClass(), armaIndividualListNewArmaIndividualToAttach.getId());
                attachedArmaIndividualListNew.add(armaIndividualListNewArmaIndividualToAttach);
            }
            armaIndividualListNew = attachedArmaIndividualListNew;
            contenedor.setArmaIndividualList(armaIndividualListNew);
            List<Explosivo> attachedExplosivoListNew = new ArrayList<Explosivo>();
            for (Explosivo explosivoListNewExplosivoToAttach : explosivoListNew) {
                explosivoListNewExplosivoToAttach = em.getReference(explosivoListNewExplosivoToAttach.getClass(), explosivoListNewExplosivoToAttach.getId());
                attachedExplosivoListNew.add(explosivoListNewExplosivoToAttach);
            }
            explosivoListNew = attachedExplosivoListNew;
            contenedor.setExplosivoList(explosivoListNew);
            contenedor = em.merge(contenedor);
            for (ArmaIndividual armaIndividualListNewArmaIndividual : armaIndividualListNew) {
                if (!armaIndividualListOld.contains(armaIndividualListNewArmaIndividual)) {
                    Contenedor oldContenedorOfArmaIndividualListNewArmaIndividual = armaIndividualListNewArmaIndividual.getContenedor();
                    armaIndividualListNewArmaIndividual.setContenedor(contenedor);
                    armaIndividualListNewArmaIndividual = em.merge(armaIndividualListNewArmaIndividual);
                    if (oldContenedorOfArmaIndividualListNewArmaIndividual != null && !oldContenedorOfArmaIndividualListNewArmaIndividual.equals(contenedor)) {
                        oldContenedorOfArmaIndividualListNewArmaIndividual.getArmaIndividualList().remove(armaIndividualListNewArmaIndividual);
                        oldContenedorOfArmaIndividualListNewArmaIndividual = em.merge(oldContenedorOfArmaIndividualListNewArmaIndividual);
                    }
                }
            }
            for (Explosivo explosivoListNewExplosivo : explosivoListNew) {
                if (!explosivoListOld.contains(explosivoListNewExplosivo)) {
                    Contenedor oldContenedorOfExplosivoListNewExplosivo = explosivoListNewExplosivo.getContenedor();
                    explosivoListNewExplosivo.setContenedor(contenedor);
                    explosivoListNewExplosivo = em.merge(explosivoListNewExplosivo);
                    if (oldContenedorOfExplosivoListNewExplosivo != null && !oldContenedorOfExplosivoListNewExplosivo.equals(contenedor)) {
                        oldContenedorOfExplosivoListNewExplosivo.getExplosivoList().remove(explosivoListNewExplosivo);
                        oldContenedorOfExplosivoListNewExplosivo = em.merge(oldContenedorOfExplosivoListNewExplosivo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contenedor.getId();
                if (findContenedor(id) == null) {
                    throw new NonexistentEntityException("The contenedor with id " + id + " no longer exists.");
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
            Contenedor contenedor;
            try {
                contenedor = em.getReference(Contenedor.class, id);
                contenedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contenedor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ArmaIndividual> armaIndividualListOrphanCheck = contenedor.getArmaIndividualList();
            for (ArmaIndividual armaIndividualListOrphanCheckArmaIndividual : armaIndividualListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contenedor (" + contenedor + ") cannot be destroyed since the ArmaIndividual " + armaIndividualListOrphanCheckArmaIndividual + " in its armaIndividualList field has a non-nullable contenedor field.");
            }
            List<Explosivo> explosivoListOrphanCheck = contenedor.getExplosivoList();
            for (Explosivo explosivoListOrphanCheckExplosivo : explosivoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contenedor (" + contenedor + ") cannot be destroyed since the Explosivo " + explosivoListOrphanCheckExplosivo + " in its explosivoList field has a non-nullable contenedor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(contenedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contenedor> findContenedorEntities() {
        return findContenedorEntities(true, -1, -1);
    }

    public List<Contenedor> findContenedorEntities(int maxResults, int firstResult) {
        return findContenedorEntities(false, maxResults, firstResult);
    }

    private List<Contenedor> findContenedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contenedor.class));
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

    public Contenedor findContenedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contenedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getContenedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contenedor> rt = cq.from(Contenedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
