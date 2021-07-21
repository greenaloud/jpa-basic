import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpastart");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            logicLegacy(em);
//            logicJpa(em);
//            logicJpaBidirectional(em);
//            logicClassCheck(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }


/*

    private static void logicLegacy(EntityManager em) {

        Team team = new Team("치킨팀");
        em.persist(team);

        Member member1 = new Member();
        member1.setUsername("김양념");
        member1.setTeamId(team.getId());
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("김후라이드");
        member2.setTeamId(team.getId());
        em.persist(member2);

    }
*/

/*
    private static void logicClassCheck(EntityManager em) {

        Team team = new Team();
        team.setName("치킨팀");
        em.persist(team);

        Member member1 = new Member();
        member1.setUsername("김양념");
        member1.setTeam(team);
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("김후라이드");
        member2.setTeam(team);
        em.persist(member2);

        em.flush();
        em.clear();

        Member foundMember = em.find(Member.class, member1.getId());
        Team memberTeam = foundMember.getTeam();
        System.out.println(memberTeam.getClass());

        Team foundTeam = (Team) em.createQuery("select t from Team t where t.name=:teamName")
                .setParameter("teamName", team.getName())
                .getSingleResult();
        System.out.println(foundTeam.getClass());

        List<Member> example = new ArrayList<>();
        System.out.println(example.getClass());

        List<Member> memberList = foundTeam.getMembers();
        System.out.println(memberList.getClass());

        for (Member member : memberList) {
            System.out.println(member.getClass());
            System.out.println(member.getUsername());
        }

    }
*/

    private static void logicJpa(EntityManager em) {

        Team team = new Team();
        team.setName("치킨팀");
        em.persist(team);

        Member member1 = new Member();
        member1.setUsername("김양념");
        member1.setTeam(team);
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("김후라이드");
        member2.setTeam(team);
        em.persist(member2);

//        em.flush();
//        em.clear();

        // 멤버 및 팀 조회 로직 작성

    }

    private static void logicJpaBidirectional(EntityManager em) {

        Team team = new Team();
        team.setName("치킨팀");
        em.persist(team);

        Member member1 = new Member();
        member1.setUsername("김양념");
        member1.setTeam(team);
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("김후라이드");
        member2.setTeam(team);
        em.persist(member2);

    }

}
