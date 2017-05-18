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
import java.util.ArrayList;
import java.util.List;
import Modelo.Civil;
import Modelo.ZonaVeredal;
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
public class ZonaVeredalJpaController implements Serializable {

    public ZonaVeredalJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("ProjectPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ZonaVeredal zonaVeredal) throws PreexistingEntityException, Exception {
        if (zonaVeredal.getIntegranteFarcepList() == null) {
            zonaVeredal.setIntegranteFarcepList(new ArrayList<IntegranteFarcep>());
        }
        if (zonaVeredal.getCivilList() == null) {
            zonaVeredal.setCivilList(new ArrayList<Civil>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<IntegranteFarcep> attachedIntegranteFarcepList = new ArrayList<IntegranteFarcep>();
            for (IntegranteFarcep integranteFarcepListIntegranteFarcepToAttach : zonaVeredal.getIntegranteFarcepList()) {
                integranteFarcepListIntegranteFarcepToAttach = em.getReference(integranteFarcepListIntegranteFarcepToAttach.getClass(), integranteFarcepListIntegranteFarcepToAttach.getCedula());
                attachedIntegranteFarcepList.add(integranteFarcepListIntegranteFarcepToAttach);
            }
            zonaVeredal.setIntegranteFarcepList(attachedIntegranteFarcepList);
            List<Civil> attachedCivilList = new ArrayList<Civil>();
            for (Civil civilListCivilToAttach : zonaVeredal.getCivilList()) {
                civilListCivilToAttach = em.getReference(civilListCivilToAttach.getClass(), civilListCivilToAttach.getCedula());
                attachedCivilList.add(civilListCivilToAttach);
            }
            zonaVeredal.setCivilList(attachedCivilList);
            em.persist(zonaVeredal);
            for (IntegranteFarcep integranteFarcepListIntegranteFarcep : zonaVeredal.getIntegranteFarcepList()) {
                ZonaVeredal oldZonaveredalOfIntegranteFarcepListIntegranteFarcep = integranteFarcepListIntegranteFarcep.getZonaveredal();
                integranteFarcepListIntegranteFarcep.setZonaveredal(zonaVeredal);
                integranteFarcepListIntegranteFarcep = em.merge(integranteFarcepListIntegranteFarcep);
                if (oldZonaveredalOfIntegranteFarcepListIntegranteFarcep != null) {
                    oldZonaveredalOfIntegranteFarcepListIntegranteFarcep.getIntegranteFarcepList().remove(integranteFarcepListIntegranteFarcep);
                    oldZonaveredalOfIntegranteFarcepListIntegranteFarcep = em.merge(oldZonaveredalOfIntegranteFarcepListIntegranteFarcep);
                }
            }
            for (Civil civilListCivil : zonaVeredal.getCivilList()) {
                ZonaVeredal oldZonaOfCivilListCivil = civilListCivil.getZona();
                civilListCivil.setZona(zonaVeredal);
                civilListCivil = em.merge(civilListCivil);
                if (oldZonaOfCivilListCivil != null) {
                    oldZonaOfCivilListCivil.getCivilList().remove(civilListCivil);
                    oldZonaOfCivilListCivil = em.merge(oldZonaOfCivilListCivil);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findZonaVeredal(zonaVeredal.getId()) != null) {
                throw new PreexistingEntityException("ZonaVeredal " + zonaVeredal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ZonaVeredal zonaVeredal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ZonaVeredal persistentZonaVeredal = em.find(ZonaVeredal.class, zonaVeredal.getId());
            List<IntegranteFarcep> integranteFarcepListOld = persistentZonaVeredal.getIntegranteFarcepList();
            List<IntegranteFarcep> integranteFarcepListNew = zonaVeredal.getIntegranteFarcepList();
            List<Civil> civilListOld = persistentZonaVeredal.getCivilList();
            List<Civil> civilListNew = zonaVeredal.getCivilList();
            List<String> illegalOrphanMessages = null;
            for (IntegranteFarcep integranteFarcepListOldIntegranteFarcep : integranteFarcepListOld) {
                if (!integranteFarcepListNew.contains(integranteFarcepListOldIntegranteFarcep)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain IntegranteFarcep " + integranteFarcepListOldIntegranteFarcep + " since its zonaveredal field is not nullable.");
                }
            }
            for (Civil civilListOldCivil : civilListOld) {
                if (!civilListNew.contains(civilListOldCivil)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Civil " + civilListOldCivil + " since its zona field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<IntegranteFarcep> attachedIntegranteFarcepListNew = new ArrayList<IntegranteFarcep>();
            for (IntegranteFarcep integranteFarcepListNewIntegranteFarcepToAttach : integranteFarcepListNew) {
                integranteFarcepListNewIntegranteFarcepToAttach = em.getReference(integranteFarcepListNewIntegranteFarcepToAttach.getClass(), integranteFarcepListNewIntegranteFarcepToAttach.getCedula());
                attachedIntegranteFarcepListNew.add(integranteFarcepListNewIntegranteFarcepToAttach);
            }
            integranteFarcepListNew = attachedIntegranteFarcepListNew;
            zonaVeredal.setIntegranteFarcepList(integranteFarcepListNew);
            List<Civil> attachedCivilListNew = new ArrayList<Civil>();
            for (Civil civilListNewCivilToAttach : civilListNew) {
                civilListNewCivilToAttach = em.getReference(civilListNewCivilToAttach.getClass(), civilListNewCivilToAttach.getCedula());
                attachedCivilListNew.add(civilListNewCivilToAttach);
            }
            civilListNew = attachedCivilListNew;
            zonaVeredal.setCivilList(civilListNew);
            zonaVeredal = em.merge(zonaVeredal);
            for (IntegranteFarcep integranteFarcepListNewIntegranteFarcep : integranteFarcepListNew) {
                if (!integranteFarcepListOld.contains(integranteFarcepListNewIntegranteFarcep)) {
                    ZonaVeredal oldZonaveredalOfIntegranteFarcepListNewIntegranteFarcep = integranteFarcepListNewIntegranteFarcep.getZonaveredal();
                    integranteFarcepListNewIntegranteFarcep.setZonaveredal(zonaVeredal);
                    integranteFarcepListNewIntegranteFarcep = em.merge(integranteFarcepListNewIntegranteFarcep);
                    if (oldZonaveredalOfIntegranteFarcepListNewIntegranteFarcep != null && !oldZonaveredalOfIntegranteFarcepListNewIntegranteFarcep.equals(zonaVeredal)) {
                        oldZonaveredalOfIntegranteFarcepListNewIntegranteFarcep.getIntegranteFarcepList().remove(integranteFarcepListNewIntegranteFarcep);
                        oldZonaveredalOfIntegranteFarcepListNewIntegranteFarcep = em.merge(oldZonaveredalOfIntegranteFarcepListNewIntegranteFarcep);
                    }
                }
            }
            for (Civil civilListNewCivil : civilListNew) {
                if (!civilListOld.contains(civilListNewCivil)) {
                    ZonaVeredal oldZonaOfCivilListNewCivil = civilListNewCivil.getZona();
                    civilListNewCivil.setZona(zonaVeredal);
                    civilListNewCivil = em.merge(civilListNewCivil);
                    if (oldZonaOfCivilListNewCivil != null && !oldZonaOfCivilListNewCivil.equals(zonaVeredal)) {
                        oldZonaOfCivilListNewCivil.getCivilList().remove(civilListNewCivil);
                        oldZonaOfCivilListNewCivil = em.merge(oldZonaOfCivilListNewCivil);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = zonaVeredal.getId();
                if (findZonaVeredal(id) == null) {
                    throw new NonexistentEntityException("The zonaVeredal with id " + id + " no longer exists.");
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
            ZonaVeredal zonaVeredal;
            try {
                zonaVeredal = em.getReference(ZonaVeredal.class, id);
                zonaVeredal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The zonaVeredal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<IntegranteFarcep> integranteFarcepListOrphanCheck = zonaVeredal.getIntegranteFarcepList();
            for (IntegranteFarcep integranteFarcepListOrphanCheckIntegranteFarcep : integranteFarcepListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ZonaVeredal (" + zonaVeredal + ") cannot be destroyed since the IntegranteFarcep " + integranteFarcepListOrphanCheckIntegranteFarcep + " in its integranteFarcepList field has a non-nullable zonaveredal field.");
            }
            List<Civil> civilListOrphanCheck = zonaVeredal.getCivilList();
            for (Civil civilListOrphanCheckCivil : civilListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ZonaVeredal (" + zonaVeredal + ") cannot be destroyed since the Civil " + civilListOrphanCheckCivil + " in its civilList field has a non-nullable zona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(zonaVeredal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ZonaVeredal> findZonaVeredalEntities() {
        return findZonaVeredalEntities(true, -1, -1);
    }

    public List<ZonaVeredal> findZonaVeredalEntities(int maxResults, int firstResult) {
        return findZonaVeredalEntities(false, maxResults, firstResult);
    }

    private List<ZonaVeredal> findZonaVeredalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ZonaVeredal.class));
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

    public ZonaVeredal findZonaVeredal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ZonaVeredal.class, id);
        } finally {
            em.close();
        }
    }

    public int getZonaVeredalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ZonaVeredal> rt = cq.from(ZonaVeredal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
