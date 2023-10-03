package com.example.restapisda;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/videos", produces = {MediaType.APPLICATION_JSON_VALUE})
public class VideoApi {
    private List<Video> videoList;

    public VideoApi() {
        this.videoList = new ArrayList<>();
        videoList.add(new Video(1L, "Top 6 React Hook Mistakes Beginners Make", "https://youtu.be/GGo3MVBFr1A?feature=shared"));
        videoList.add(new Video(2L, "All 12 useState & useEffect", "https://youtu.be/-yIsQPp31L0?feature=shared"));
        videoList.add(new Video(3L, "React's Billion Dollar Problem", "https://youtu.be/FzI7faHqM0M?feature=shared"));
    }

    @GetMapping
    public ResponseEntity<List<Video>> getVideos() {
        return ResponseEntity.ok(videoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideoById(@PathVariable Long id) {
        Optional<Video> first = videoList.stream().filter(video -> video.getId() == id).findFirst();
        if (first.isPresent()) {
            return ResponseEntity.ok(first.get());

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity addVideo(@RequestBody Video video) {
        boolean add = videoList.add(video);
        if (add) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity modVideo(@RequestBody Video newVideo) {
        Optional<Video> first = videoList.stream().filter(video -> video.getId() == newVideo.getId()).findFirst();
        if (first.isPresent()) {
            videoList.remove(first.get());
            videoList.add(newVideo);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity removeVideos(@PathVariable Long id){
        Optional<Video> first = videoList.stream().filter(video -> video.getId() == id).findFirst();
        if (first.isPresent()){
            videoList.remove(first.get());
        return ResponseEntity.ok().build();
        }else {
            return  ResponseEntity.notFound().build();
        }
    }
}


