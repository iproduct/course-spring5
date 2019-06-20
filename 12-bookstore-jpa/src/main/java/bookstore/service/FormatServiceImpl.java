package bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bookstore.dao.FormatRepository;
import bookstore.exception.EntityExistsException;
import bookstore.exception.EntityNotFoundException;
import bookstore.model.Format;

@Service
public class FormatServiceImpl implements FormatService {

	@Autowired
	private FormatRepository formatRepository;

	@Override
	public List<Format> getAll() {
		return formatRepository.findAll();
	}

	@Override
	public Format getById(int id) throws EntityNotFoundException {
		return formatRepository.findById(id).orElseThrow(() -> 
		new EntityNotFoundException("Format with ID=" + id + " not found."));
	}

	@Override
	public Format create(Format format) throws EntityExistsException {
		if (format.getId() > 0 && getById(format.getId()) != null)
			throw new EntityExistsException("Entity with ID=" + format.getId() + " already exists.");
		return formatRepository.save(format);
	}

	@Override
	public Format update(Format format) throws EntityNotFoundException {
		getById(format.getId());
		return formatRepository.save(format);
	}

	@Override
	public Format delete(int formatId) throws EntityNotFoundException {
		Format format = getById(formatId);
		formatRepository.deleteById(formatId);
		return format;
	}

}
