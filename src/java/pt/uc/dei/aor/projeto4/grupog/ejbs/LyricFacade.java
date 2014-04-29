/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.uc.dei.aor.projeto4.grupog.ejbs;

import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pt.uc.dei.aor.projeto4.grupog.entities.AppUser;
import pt.uc.dei.aor.projeto4.grupog.entities.Lyric;
import pt.uc.dei.aor.projeto4.grupog.entities.Music;

/**
 *
 * @author Bruno Maricato
 */
@Stateless
public class LyricFacade extends AbstractFacade<Lyric> {

    @PersistenceContext(unitName = "GetPlayWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LyricFacade() {
        super(Lyric.class);
    }

    public Lyric findLyric(Music music, AppUser appUser) throws Exception {
        TypedQuery<Lyric> q;
        q = em.createNamedQuery("Lyric.findLyricByMusic&User", Lyric.class);
        try {
            q.setParameter("music", music).setParameter("appuser", appUser);
            return q.getSingleResult();
        } catch (Exception e) {
            throw new Exception(e.getCause().getMessage());
        }
    }

    public boolean existLyricUser(Music music, AppUser appUser) {
        TypedQuery<Integer> q;
        q = em.createNamedQuery("Lyric.findLyricByMusic&User", Integer.class);
        q.setParameter("music", music).setParameter("appuser", appUser);
        if (q.getSingleResult() == 1) {
            return true;
        }
        return false;
    }

    public void editLyric(Lyric lyric) {
        getEntityManager().merge(lyric);
        AppUser appUser = lyric.getAppuser();
        Music m = lyric.getMusic();
        //vai à tabela AppUser e actualiza a lista de lyrics
        for (Lyric l : appUser.getLyrics()) {
            if (Objects.equals(l.getAppuser().getUser_id(), appUser.getUser_id())) {
                l.setTextLyric(lyric.getTextLyric());
                getEntityManager().merge(appUser);
            }
        }
        //vai à tabela Music e actualiza a lista de lyrics
        for (Lyric l : m.getLyrics()) {
            if (Objects.equals(l.getMusic().getMusic_id(), m.getMusic_id())) {
                l.setTextLyric(lyric.getTextLyric());
                getEntityManager().merge(m);
            }
        }

    }

}
