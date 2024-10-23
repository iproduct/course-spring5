package course.spring.presenter.factory;

import course.spring.domain.PostProvider;
import course.spring.presenter.PostPresenter;
import course.spring.presenter.impl.ConsolePostPresenter;
import lombok.extern.java.Log;

@Log
public class PresenterFactory {
    public PostPresenter createPresenter(PostProvider provider) {
        log.info("Presenter created by factory method.");
        return new ConsolePostPresenter(provider);
    }
}
