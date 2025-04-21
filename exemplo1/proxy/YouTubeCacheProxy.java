package padroesprojeto.proxy.exemplo1.proxy;

import java.util.HashMap;

import padroesprojeto.proxy.exemplo1.youtubeLib.ThirdPartyYouTubeClass;
import padroesprojeto.proxy.exemplo1.youtubeLib.ThirdPartyYouTubeLib;
import padroesprojeto.proxy.exemplo1.youtubeLib.Video;

//Proxy irá implementar a interface ThirdPartyYouTubeLib para agir como um proxy da classe ThirdPartyYouTubeClass
//A classe ThirdPartyYouTubeClass é a implementação real do serviço de vídeo do YouTube
public class YouTubeCacheProxy implements ThirdPartyYouTubeLib {

    //Atributos da classe
    private ThirdPartyYouTubeLib youtubeService;
    private HashMap<String, Video> cachePopular = new HashMap<String, Video>();
    private HashMap<String, Video> cacheAll = new HashMap<String, Video>();

    //Construtor da classe
    //Inicializa o youtubeService com uma nova instância de ThirdPartyYouTubeClass
    //Isso significa que o proxy irá usar a implementação real do serviço de vídeo do YouTube
    //O proxy irá armazenar em cache os vídeos populares e todos os vídeos
    public YouTubeCacheProxy() {
        this.youtubeService = new ThirdPartyYouTubeClass();
    }

    //Método que retorna os vídeos populares
    //Se o cachePopular estiver vazio, ele chama o método popularVideos() do youtubeService
    //Caso contrário, ele retorna o cachePopular
    //Isso significa que o proxy irá armazenar em cache os vídeos populares
    //O proxy irá evitar chamadas desnecessárias ao serviço real, economizando tempo e recursos
    @Override
    public HashMap<String, Video> popularVideos() {
        if (cachePopular.isEmpty()) {
            cachePopular = youtubeService.popularVideos();
        } else {
            System.out.println("Retrieved list from cache.");
        }
        return cachePopular;
    }

    @Override
    public Video getVideo(String videoId) {
        Video video = cacheAll.get(videoId);
        if (video == null) {
            video = youtubeService.getVideo(videoId);
            cacheAll.put(videoId, video);
        } else {
            System.out.println("Retrieved video '" + videoId + "' from cache.");
        }
        return video;
    }

    //Método para limpar o cache
    //Esse método é útil para liberar memória ou atualizar os dados em cache
    public void reset() {
        cachePopular.clear();
        cacheAll.clear();
    }
    
}
