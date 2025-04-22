package exemplo1;

import exemplo1.downloader.YouTubeDownloader;
import exemplo1.proxy.YouTubeCacheProxy;
import exemplo1.youtubeLib.ThirdPartyYouTubeClass;

public class Demo {
    
    public static void main(String[] args) {

        //Downloader necessita de uma API de terceiros para funcionar.
        //A API de terceiros é uma classe que implementa a interface ThirdPartyYouTubeLib.
        //NaiveDownloader é um downloader que apenas utiliza a API de terceiros.
        //SmartDownloader é um downloader que utiliza a API de terceiros e um proxy para cache.
        YouTubeDownloader naiveDownloader = new YouTubeDownloader(new ThirdPartyYouTubeClass());
        YouTubeDownloader smartDownloader = new YouTubeDownloader(new YouTubeCacheProxy());

        //Testes de desempenho dos dois downloaders.
        //O tempo de download do naiveDownloader é o tempo que o downloader leva para baixar os videos.
        //O tempo de download do smartDownloader é o tempo que o downloader leva para baixar os videos, mas com cache.
        //O tempo de download do smartDownloader deve ser menor que o do naiveDownloader.
        long naive = test(naiveDownloader);
        long smart = test(smartDownloader);
        System.out.print("Time saved by caching proxy: " + (naive - smart) + "ms");

    }

    //Metodo que simula o comportamento do usuario na aplicacao.
    private static long test(YouTubeDownloader downloader) {

        long startTime = System.currentTimeMillis();

        // User behavior in our app:
        //Simulando o comportamento do usuario na aplicacao.
        downloader.renderPopularVideos();
        downloader.renderVideoPage("catzzzzzzzzz");
        downloader.renderPopularVideos();
        downloader.renderVideoPage("dancesvideoo");
        // Users might visit the same page quite often.
        //Os usuarios podem visitar a mesma pagina varias vezes.
        downloader.renderVideoPage("catzzzzzzzzz");
        downloader.renderVideoPage("someothervid");

        //Tempo estimado para o download dos videos.
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.print("Time elapsed: " + estimatedTime + "ms\n");
        return estimatedTime;
    }

}
