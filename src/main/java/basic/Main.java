package basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
            logicRemove(em);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }

    private static void logicRemove(EntityManager em) {

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

        member1.setTeam(null);
        member2.setTeam(null);
        em.remove(team);

    }


/*
    private static void logicLegacy(EntityManager em) {

        Team team = new Team();
        team.setName("치킨팀");
        em.persist(team);

        Member member1 = new Member();
        member1.setUsername("김양념");
        member1.setTeamId(team.getId());
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("김후라이드");
        member2.setTeamId(team.getId());
        em.persist(member2);

        Member foundMember = em.find(Member.class, member1.getId());
        Long teamId = foundMember.getTeamId();
        Team foundTeam = em.find(Team.class, teamId);
        System.out.println(foundTeam.getName());

    }*/

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

//        em.flush();
//        em.clear();
//
//        Member foundMember = em.find(Member.class, member1.getId());
//        Team memberTeam = foundMember.getTeam();
//        System.out.println(memberTeam.getClass());

        Team foundTeam = (Team) em.createQuery("select t from Team t where t.name=:teamName")
                .setParameter("teamName", team.getName())
                .getSingleResult();
        System.out.println(foundTeam.getClass());

//        List<Member> example = new ArrayList<>();
//        System.out.println(example.getClass());

        List<Member> memberList = foundTeam.getMembers();
        System.out.println(memberList.size());
        System.out.println(memberList.getClass());

        for (Member member : memberList) {
            System.out.println(member.getClass());
            System.out.println(member.getUsername());
        }

    }

    private static void logicJpa(EntityManager em) {

        Team team = new Team();
        team.setName("치킨팀");
        em.persist(team);
        System.out.println(team.getMembers().size());

        Member member1 = new Member();
        member1.setUsername("김양념");
        member1.setTeam(team);
        em.persist(member1);
        System.out.println(team.getMembers().size());

        Member member2 = new Member();
        member2.setUsername("김후라이드");
        member2.setTeam(team);
        em.persist(member2);
        System.out.println(team.getMembers().size());

        em.flush();
        em.clear();

        // 멤버 및 팀 조회 로직 작성
        Member foundMember = em.find(Member.class, member1.getId());
        System.out.println(foundMember.getTeam().getName());

        Team foundTeam = em.find(Team.class, team.getId());
        System.out.println(team==foundTeam);
        System.out.println(foundTeam.getMembers().size());

        foundTeam.getMembers().iterator().forEachRemaining(m -> System.out.println(m.getUsername()));

    }

    private static void logicJpaBidirectional(EntityManager em) {

        Team team = new Team();
        team.setName("치킨팀");
        em.persist(team);

        Team team1 = new Team();
        team1.setName("피자팀");
        em.persist(team1);

        Member member1 = new Member();
        member1.setUsername("김양념");
        member1.setTeam(team);
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("김후라이드");
        member2.setTeam(team);
        em.persist(member2);

        member2.setTeam(team1);

//        em.flush();
//        em.clear();

        // 멤버 및 팀 조회 로직 작성
        System.out.println("============================");
        Member foundMember = em.find(Member.class, member1.getId());
        System.out.println(foundMember.getTeam().getName());

        Team foundTeam = em.find(Team.class, team.getId());
        List<Member> members = foundTeam.getMembers();

        for (Member member : members) {
            System.out.println(member.getUsername());
        }
        System.out.println("============================");

    }

}
