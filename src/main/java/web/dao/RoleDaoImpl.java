package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveRole(Set<Role> roles) {
        for (Role role: roles) {
            em.persist(role);
        }
    }
    @Override
    public Set<Role> getAllRoles() {
        return em.createQuery("select r from Role r",Role.class).getResultList().stream().collect(Collectors.toSet());
    }

    @Override
    public Role findRole(Role role) {
        return em.createQuery("SELECT r FROM Role r WHERE r.name=:name", Role.class)
                .setParameter("name", role.toString())
                .getSingleResult();
    }
}
