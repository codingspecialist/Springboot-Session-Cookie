package shop.mtcoding.hiberpc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import shop.mtcoding.hiberpc.model.reply.ReplyRepository;

@RequiredArgsConstructor
@RestController
public class ReplyController {
    private final ReplyRepository replyRepository;
}
