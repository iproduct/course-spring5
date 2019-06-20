package bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.dao.PublisherRepository;
import bookstore.exception.EntityExistsException;
import bookstore.exception.EntityNotFoundException;
import bookstore.model.Publisher;

@Service
public class PublisherServiceImpl implements PublisherService {

	@Autowired
	private PublisherRepository publisherRepository;

	@Override
	public List<Publisher> getAll() {
		return publisherRepository.findAll();
	}

	@Override
	public Publisher getById(int id) throws EntityNotFoundException {
		return publisherRepository.findById(id).orElseThrow(() -> 
		new EntityNotFoundException("Publisher with ID=" + id + " not found."));
	}

	@Override
	public Publisher create(Publisher publisher) throws EntityExistsException {
		if (publisher.getId() > 0 && getById(publisher.getId()) != null)
			throw new EntityExistsException("Entity with ID=" + publisher.getId() + " already exists.");
		return publisherRepository.save(publisher);
	}

	@Override
	public Publisher update(Publisher publisher) throws EntityNotFoundException {
		getById(publisher.getId());
		return publisherRepository.save(publisher);
	}

	@Override
	public Publisher delete(int publisherId) throws EntityNotFoundException {
		Publisher publisher = getById(publisherId);
		publisherRepository.deleteById(publisherId);
		return publisher;
	}

}
