package project.ping.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.ping.apiPayload.exception.GeneralException;
import project.ping.apiPayload.status.ErrorStatus;
import project.ping.converter.CommentsConverter;
import project.ping.domain.Comments;
import project.ping.domain.Member;
import project.ping.domain.Post;
import project.ping.dto.CommentsRequestDTO;
import project.ping.dto.CommentsResponseDTO;
import project.ping.repository.CommentsRepository;
import project.ping.repository.PostRepository;
import project.ping.security.auth.MemberDetail;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentsService {

    private final PostRepository postRepository;
    private final CommentsRepository commentsRepository;

    public CommentsResponseDTO.CommentsResultDTO writeComments(MemberDetail memberDetail, CommentsRequestDTO.WriteCommentsDTO request) {
        Member member = memberDetail.getMember();
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_EXIST_POST));
        Comments comments = commentsRepository.findById(request.getCommentId()).orElse(null);
        Comments newComments = CommentsConverter.toComments(member, post, comments, request);
        commentsRepository.save(newComments);

        return CommentsConverter.toCommentsResult(newComments, post);
    }

    public CommentsResponseDTO.CommentsDTO updateComments(MemberDetail memberDetail, CommentsRequestDTO.UpdateCommentsDTO request) {
        Comments comments = commentsRepository.findById(request.getCommentId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_EXIST_COMMENTS));
        if(comments.getMember().getId() != memberDetail.getMember().getId()){
            throw new GeneralException(ErrorStatus.NOT_MATCH_COMMENT_MEMBER);
        }
        comments.updateContent(request.getContent());
        return CommentsConverter.completeComments(comments);
    }

    public CommentsResponseDTO.CommentsResultDTO deleteComments(MemberDetail memberDetail, Long commentsId) {
        Comments comments = commentsRepository.findById(commentsId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_EXIST_COMMENTS));
        if(comments.getMember().getId() != memberDetail.getMember().getId()){
            throw new GeneralException(ErrorStatus.NOT_MATCH_COMMENT_MEMBER);
        }
        comments.updateContent("삭제된 댓글입니다.");

        return CommentsConverter.toCommentsResult(comments, comments.getPost());
    }

    public List<CommentsResponseDTO.CommentsListDTO> getComments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new GeneralException(ErrorStatus.NOT_EXIST_POST));
        List<Comments> commentsList = commentsRepository.findByPost(post); // 댓글, 대댓글 모두 가져오기
        return CommentsConverter.toCommentsList(commentsList);
    }
}
