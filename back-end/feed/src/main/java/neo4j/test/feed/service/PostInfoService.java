package neo4j.test.feed.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo4j.test.feed.model.dto.LikePostDto;
import neo4j.test.feed.model.dto.PostInfoDto;
import neo4j.test.feed.model.entity.PostInfo;
import neo4j.test.feed.model.entity.Tag;
import neo4j.test.feed.model.entity.UserInfo;
import neo4j.test.feed.model.relationship.Like;
import neo4j.test.feed.model.relationship.Post;
import neo4j.test.feed.repository.PostInfoRepository;
import neo4j.test.feed.repository.UserInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostInfoService {
    private final PostInfoRepository postInfoRepository;
    private final UserInfoRepository userInfoRepository;

    private PostInfo postInfoDtoToPostInfo(PostInfoDto dto) {
        return new PostInfo(dto);
    }

    private PostInfoDto postInfoToPostInfoDto(PostInfo info) {
        return new PostInfoDto(info);
    }

    /**
     * 포스트 정보 저장
     * @param postInfoDto
     * @return
     * @throws Exception
     */
    @Transactional
    public PostInfoDto savePostInfo(PostInfoDto postInfoDto) throws Exception {
        Optional<UserInfo> userInfo = Optional.ofNullable(userInfoRepository.findByUserId(postInfoDto.getUserId()));
        if(userInfo.isPresent()) {

            UserInfo user = userInfo.get();
            PostInfo postInfo = postInfoDtoToPostInfo(postInfoDto);


            Set<Tag> postTags = postInfo.getTags();

            Post post = new Post(postInfo);
            user.getPosts().add(post);
            userInfoRepository.save(user);
            return postInfoToPostInfoDto(postInfoRepository.save(postInfo));
        }
        else throw new Exception("사용자 정보가 없습니다.");
    }

    /**
     * 포스트 좋아요 하기
     * @param likePostDto
     * @return
     * @throws Exception
     */
    @Transactional
    public PostInfoDto likePostInfo(LikePostDto likePostDto) throws Exception {

        Optional<UserInfo> user = Optional.ofNullable(userInfoRepository.existsLike(likePostDto.getPostId(), likePostDto.getUserId()));
        if (user.isEmpty()) {
            Optional<UserInfo> userInfo = Optional.ofNullable(userInfoRepository.findByUserId(likePostDto.getUserId()));
            Optional<PostInfo> postInfo = Optional.ofNullable(postInfoRepository.findNodeById(likePostDto.getPostId()));
            if (userInfo.isPresent() && postInfo.isPresent()) {
                Like like = new Like(userInfo.get());
                PostInfo post = postInfo.get();
                post.getLikes().add(like);
                return postInfoToPostInfoDto(postInfoRepository.save(post));
            } else {
                throw new Exception("해당 정보가 없습니다.");
            }
        } else {
            throw new Exception("이미 like 한 게시물입니다");
        }
    }

    /**
     * 포스트 좋아요 취소
     * @param likePostDto
     * @return
     * @throws Exception
     */
    @Transactional
    public PostInfo dislikePostInfo(LikePostDto likePostDto) throws Exception {
        Optional<UserInfo> user = Optional.ofNullable(userInfoRepository.existsLike(likePostDto.getPostId(), likePostDto.getUserId()));
        if (user.isPresent()) {
            Optional<UserInfo> userInfo = Optional.ofNullable(userInfoRepository.findByUserId(likePostDto.getUserId()));
            Optional<PostInfo> postInfo = Optional.ofNullable(postInfoRepository.findNodeById(likePostDto.getPostId()));
            if (userInfo.isPresent() && postInfo.isPresent()) {
                return postInfoRepository.dislikePost(likePostDto.getPostId(), likePostDto.getUserId());
            } else {
                throw new Exception("해당 정보가 없습니다.");
            }
        } else {
            throw new Exception("해당 정보가 없습니다.");
        }
    }
}
