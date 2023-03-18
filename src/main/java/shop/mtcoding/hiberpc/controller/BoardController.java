package shop.mtcoding.hiberpc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import shop.mtcoding.hiberpc.model.board.BoardRepository;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardRepository boardRepository;
}
