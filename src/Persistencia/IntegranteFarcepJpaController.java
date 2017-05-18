/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Modelo.IntegranteFarcep;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.ZonaVeredal;
import Modelo.Pqr;
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
public class IntegranteFarcepJpaController implements Serializable {

    public IntegranteFarcepJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("ProjectPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IntegranteFarcep integranteFarcep) throws PreexistingEntityException, Exception {
        if (integranteFarcep.getPqrList() == null) {
            integranteFarcep.setPqrList(new ArrayList<Pqr>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ZonaVeredal zonaveredal = integranteFarcep.getZonaveredal();
            if (zonaveredal != null) {
                zonaveredal = em.getReference(zonaveredal.getClass(), zonaveredal.getId());
                integranteFarcep.setZonaveredal(zonaveredal);
            }
            List<Pqr> attachedPqrList = new ArrayList<Pqr>();
            for (Pqr pqrListPqrToAttach : integranteFarcep.getPqrList()) {
                pqrListPqrToAttach = em.getReference(pqrListPqrToAttach.getClass(), pqrListPqrToAttach.getId());
                attachedPqrList.add(pqrListPqrToAttach);
            }
            integranteFarcep.setPqrList(attachedPqrList);
            em.persist(integranteFarcep);
            if (zonaveredal != null) {
                zonaveredal.getIntegranteFarcepList().add(integranteFarcep);
                zonaveredal = em.merge(zonaveredal);
            }
            for (Pqr pqrListPqr : integranteFarcep.getPqrList()) {
                IntegranteFarcep oldCedulaOfPqrListPqr = pqrListPqr.getCedula();
                pqrListPqr.setCedula(integranteFarcep);
                pqrListPqr = em.merge(pqrListPqr);
                if (oldCedulaOfPqrListPqr != null) {
                    oldCedulaOfPqrListPqr.getPqrList().remove(pqrListPqr);
                    oldCedulaOfPqrListPqr = em.merge(oldCedulaOfPqrListPqr);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIntegranteFarcep(integranteFarcep.getCedula()) != null) {
                throw new PreexistingEntityException("IntegranteFarcep " + integranteFarcep + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IntegranteFarcep integranteFarcep) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IntegranteFarcep persistentIntegranteFarcep = em.find(IntegranteFarcep.class, integranteFarcep.getCedula());
            ZonaVeredal zonaveredalOld = persistentIntegranteFarcep.getZonaveredal();
            ZonaVeredal zonaveredalNew = integranteFarcep.getZonaveredal();
            List<Pqr> pqrListOld = persistentIntegranteFarcep.getPqrList();
            List<Pqr> pqrListNew = integranteFarcep.getPqrList();
            List<String> illegalOrphanMessages = null;
            for (Pqr pqrListOldPqr : pqrListOld) {
                if (!pqrListNew.contains(pqrListOldPqr)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pqr " + pqrListOldPqr + " since its cedula field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (zonaveredalNew != null) {
                zonaveredalNew = em.getReference(zonaveredalNew.getClass(), zonaveredalNew.getId());
                integranteFarcep.setZonaveredal(zonaveredalNew);
            }
            List<Pqr> attachedPqrListNew = new ArrayList<Pqr>();
            for (Pqr pqrListNewPqrToAttach : pqrListNew) {
                pqrListNewPqrToAttach = em.getReference(pqrListNewPqrToAttach.getClass(), pqrListNewPqrToAttach.getId());
                attachedPqrListNew.add(pqrListNewPqrToAttach);
            }
            pqrListNew = attachedPqrListNew;
            integranteFarcep.setPqrList(pqrListNew);
            integranteFarcep = em.merge(integranteFarcep);
            if (zonaveredalOld != null && !zonaveredalOld.equals(zonaveredalNew)) {
                zonaveredalOld.getIntegranteFarcepList().remove(integranteFarcep);
                zonaveredalOld = em.merge(zonaveredalOld);
            }
            if (zonaveredalNew != null && !zonaveredalNew.equals(zonaveredalOld)) {
                zonaveredalNew.getIntegranteFarcepList().add(integranteFarcep);
                zonaveredalNew = em.merge(zonaveredalNew);
            }
            for (Pqr pqrListNewPqr : pqrListNew) {
                if (!pqrListOld.contains(pqrListNewPqr)) {
                    IntegranteFarcep oldCedulaOfPqrListNewPqr = pqrListNewPqr.getCedula();
                    pqrListNewPqr.setCedula(integranteFarcep);
                    pqrListNewPqr = em.merge(pqrListNewPqr);
                    if (oldCedulaOfPqrListNewPqr != null && !oldCedulaOfPqrListNewPqr.equals(integranteFarcep)) {
                        oldCedulaOfPqrListNewPqr.getPqrList().remove(pqrListNewPqr);
                        oldCedulaOfPqrListNewPqr = em.merge(oldCedulaOfPqrListNewPqr);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = integranteFarcep.getCedula();
                if (findIntegranteFarcep(id) == null) {
                    throw new NonexistentEntityException("The integranteFarcep with id " + id + " no longer exists.");
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
            IntegranteFarcep integranteFarcep;
            try {
                integranteFarcep = em.getReference(IntegranteFarcep.class, id);
                integranteFarcep.getCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The integranteFarcep with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pqr> pqrListOrphanCheck = integranteFarcep.getPqrList();
            for (Pqr pqrListOrphanCheckPqr : pqrListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This IntegranteFarcep (" + integranteFarcep + ") cannot be destroyed since the Pqr " + pqrListOrphanCheckPqr + " in its pqrList field has a non-nullable cedula field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ZonaVeredal zonaveredal = integranteFarcep.getZonaveredal();
            if (zonaveredal != null) {
                zonaveredal.getIntegranteFarcepList().remove(integranteFarcep);
                zonaveredal = em.merge(zonaveredal);
            }
            em.remove(integranteFarcep);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IntegranteFarcep> findIntegranteFarcepEntities() {
        return findIntegranteFarcepEntities(true, -1, -1);
    }

    public List<IntegranteFarcep> findIntegranteFarcepEntities(int maxResults, int firstResult) {
        return findIntegranteFarcepEntities(false, maxResults, firstResult);
    }

    private List<IntegranteFarcep> findIntegranteFarcepEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IntegranteFarcep.class));
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

    public IntegranteFarcep findIntegranteFarcep(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IntegranteFarcep.class, id);
        } finally {
            em.close();
        }
    }

    public int getIntegranteFarcepCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IntegranteFarcep> rt = cq.from(IntegranteFarcep.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
