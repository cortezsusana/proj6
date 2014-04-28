/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.uc.dei.aor.projeto4.grupog.WebService;

import com.chartlyrics.api.GetLyricResult;

/**
 *
 * @author Elsa
 */
public class ChartLyricSoap {

    public ChartLyricSoap() {
    }

    public String getLyricSong(String artist, String song) {
        GetLyricResult glr = searchLyricDirect(artist, song);
        return glr.getLyric();
    }

    private static GetLyricResult searchLyricDirect(java.lang.String artist, java.lang.String song) {
        com.chartlyrics.api.Apiv1 service = new com.chartlyrics.api.Apiv1();
        com.chartlyrics.api.Apiv1Soap port = service.getApiv1Soap();
        return port.searchLyricDirect(artist, song);
    }

}
