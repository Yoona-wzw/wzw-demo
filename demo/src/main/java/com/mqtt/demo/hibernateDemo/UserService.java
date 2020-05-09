package com.mqtt.demo.hibernateDemo;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 *
 * @Param
 * @return
**/
@Service
@Transactional
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    public UserEntity findUserByName(String name){
        UserEntity byUserName = userRepository.findByUserName(name);
        return byUserName;
    }
    public List find(){
//        Session session = entityManager.unwrap(Session.class);
//        Query query = (Query) entityManager.createNativeQuery("select * from user_entity");
//        List list = query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
//       return list;
        //NativeQuery sqlQuery = session.createSQLQuery("select * from user_entity");
       /*Query query = session.createQuery("select t from UserEntity t");
       query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List list = query.list();
        return list;*/
       return null;
    }
    /**
     *
     * @Param [userEntity]
     * @return
    **/

    public void saveUser(UserEntity userEntity){

//        Query qu = entityManager.createNativeQuery("insert into user_entity (id,username,password) values(" + userEntity.getId() + ",'" + userEntity.getUsername() + "','" + userEntity.getPassword() + "')");
//       qu.executeUpdate();
        Session session = entityManager.unwrap(Session.class);
        session.save(userEntity);



    }
}
