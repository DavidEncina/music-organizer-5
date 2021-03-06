import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * A class to hold details of audio tracks.
 * Individual tracks may be played.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class MusicOrganizer
{
    // An ArrayList for storing music tracks.
    private ArrayList<Track> tracks;
    // A player for the music tracks.
    private MusicPlayer player;
    // A reader that can read music files and load them as tracks.
    private TrackReader reader;
    // Almacena si se esta reproduciendo una cancion en este momento.
    private boolean seEstaReproduciendo;
    /**
     * Create a MusicOrganizer
     * Al crear un objeto MusicOrganizer, se indica por par�metro el nombre de la carpeta donde est�n los archivos mp3 que manejar� el organizador.
     */
    public MusicOrganizer(String nombreCarpetaMusica)
    {
        tracks = new ArrayList<Track>();
        player = new MusicPlayer();
        reader = new TrackReader();
        readLibrary(nombreCarpetaMusica);
        System.out.println("Music library loaded. " + getNumberOfTracks() + " tracks.");
        System.out.println();
        seEstaReproduciendo = false;        
    }
    
    /**
     * Add a track file to the collection.
     * @param filename The file name of the track to be added.
     */
    public void addFile(String filename)
    {
        tracks.add(new Track(filename));
    }
    
    /**
     * Add a track to the collection.
     * @param track The track to be added.
     */
    public void addTrack(Track track)
    {
        tracks.add(track);
    }
    
    /**
     * Play a track in the collection.
     * @param index The index of the track to be played.
     * Al usar el metodo se incrementa el contador de la cancion en uno.
     */
    public void playTrack(int index)
    {
        if (seEstaReproduciendo == true) {
            System.out.println("Ya hay una cancion reproduciendose. No se puede reproducir otra");
        }
        else if (indexValid(index)) {
            Track track = tracks.get(index);
            player.startPlaying(track.getFilename());
            System.out.println("Now playing: " + track.getArtist() + " - " + track.getTitle());
            track.incrementarContadorReproducciones();
            seEstaReproduciendo = true;
        }
    }
    
    /**
     * Return the number of tracks in the collection.
     * @return The number of tracks in the collection.
     */
    public int getNumberOfTracks()
    {
        return tracks.size();
    }
    
    /**
     * List a track from the collection.
     * @param index The index of the track to be listed.
     */
    public void listTrack(int index)
    {
        System.out.print("Track " + index + ": ");
        Track track = tracks.get(index);
        System.out.println(track.getDetails());
    }
    
    /**
     * Show a list of all the tracks in the collection.
     */
    public void listAllTracks()
    {
        System.out.println("Track listing: ");

        for(Track track : tracks) {
            System.out.println(track.getDetails());
        }
        System.out.println();
    }
    
    /**
     * List all tracks by the given artist.
     * @param artist The artist's name.
     */
    public void listByArtist(String artist)
    {
        for(Track track : tracks) {
            if(track.getArtist().contains(artist)) {
                System.out.println(track.getDetails());
            }
        }
    }
    
    /**
     * Remove a track from the collection.
     * @param index The index of the track to be removed.
     */
    public void removeTrack(int index)
    {
        if(indexValid(index)) {
            tracks.remove(index);
        }
    }
    
    /**
     * Play the first track in the collection, if there is one.
     */
    public void playFirst()
    {
        if (seEstaReproduciendo == true) {
            System.out.println("Ya hay una cancion reproduciendose. No se puede reproducir otra");
        }
        else if (tracks.size() > 0) {
            player.startPlaying(tracks.get(0).getFilename());
            seEstaReproduciendo = true;
        }
    }
    
    /**
     * Stop the player.
     */
    public void stopPlaying()
    {
        player.stop();
        seEstaReproduciendo = false;
    }

    /**
     * Determine whether the given index is valid for the collection.
     * Print an error message if it is not.
     * @param index The index to be checked.
     * @return true if the index is valid, false otherwise.
     */
    private boolean indexValid(int index)
    {
        // The return value.
        // Set according to whether the index is valid or not.
        boolean valid;
        
        if(index < 0) {
            System.out.println("Index cannot be negative: " + index);
            valid = false;
        }
        else if(index >= tracks.size()) {
            System.out.println("Index is too large: " + index);
            valid = false;
        }
        else {
            valid = true;
        }
        return valid;
    }
    
    private void readLibrary(String folderName)
    {
        ArrayList<Track> tempTracks = reader.readTracks(folderName, ".mp3");

        // Put all thetracks into the organizer.
        for(Track track : tempTracks) {
            addTrack(track);
        }
    }
    
    /**
     * Se introduce una cadena y muestre por pantalla la informaci�n de los tracks que contienen dicha cadena en el t�tulo de la canci�n.
     */
    public void findInTitle(String nombreDeCancion)
    {
        for (Track track : tracks) {
            if (track.getTitle().contains(nombreDeCancion)) {
                System.out.println(track.getDetails());
            }
        }
    }
    
    /**
     * Resetea el contador de reproduccion de la cancion a 0.
     */
    public void resetearContador(int index)
    {
        if(indexValid(index)) {
            Track track = tracks.get(index);            
            track.resetearContadorReproducciones();
        }
        else {
            System.out.println("El indice introducido no puede ser negativo");
        }
    }
    
    /**
     * Informa por pantalla de si en este momento se est� reproduciendo un track completo o si no 
     */
    public void isPlaying()
    {
        if (seEstaReproduciendo == false){
            System.out.println("No se esta reproduciendo ninguna cancion");
        }
        else {
            System.out.println("Se esta reproduciendo una cancion");
        }
    }
    
    /**
     * Muestra los detalles de todos los tracks almacenados en un organizador usando un iterador.
     */
    public void listAllTrackWithIterator()
    {
        Iterator<Track> it = tracks.iterator();
        while (it.hasNext()) {
            Track track = it.next();
            System.out.println(track.getDetails());
        }
    }
    
    /**
     * Permite eliminar del organizador los tracks que contengan un determinado artista usando un iterador.
     */
    public void removeByArtist(String nombreArtista)
    {
        Iterator<Track> it = tracks.iterator();
        while(it.hasNext()) {
            Track nombre = it.next();
            if (nombre.getArtist().contains(nombreArtista)) {
                it.remove();
            }
        }
    }
    
    /**
     * Permite eliminar del organizador los tracks que contengan una determinada cadena en el t�tulo de la canci�n usando un iterador.
     */
    public void removeByTitle(String nombreCancion)
    {
        Iterator<Track> it = tracks.iterator();
        while(it.hasNext()) {
            Track nombre = it.next();
            if (nombre.getTitle().contains(nombreCancion)) {
                it.remove();
            }
        }
    }
    
    /**
     * Reproduce una de las canciones del organizador al azar.
     */
    public void playRandom()
    {
        Random cancion = new Random();
        playTrack(cancion.nextInt(tracks.size()));
    }
}
