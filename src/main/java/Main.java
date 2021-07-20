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

        try {
            tx.begin();
            logic(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }

    private static void logic(EntityManager em) {

        Team team1 = new Team("team1", "호랑이팀");
        em.persist(team1);

        Member member1 = new Member("member1", "홍길동");
        member1.setTeam(team1); // 연관관계 설정, 회원 -> 팀
        em.persist(member1);

        Member member2 = new Member("member2", "김길동");
        member2.setTeam(team1);
        em.persist(member2);

        em.flush();
        em.clear();

        // 객체 그래프 탐색 : 객체를 통해 연관된 엔티티를 조회
        // 회원 -> 팀
        Member findMember = em.find(Member.class, "member1");
        Team getTeam = findMember.getTeam();
        System.out.println("팀 이름 = " + getTeam.getName());

        // 팀 -> 회원
        Team findTeam = em.find(Team.class, "team1");
        List<Member> getMembers = findTeam.getMembers();

        for (Member member : getMembers) {
            System.out.println("팀에 소속된 회원의 이름 = " + member.getUsername());
        }

    }

}
