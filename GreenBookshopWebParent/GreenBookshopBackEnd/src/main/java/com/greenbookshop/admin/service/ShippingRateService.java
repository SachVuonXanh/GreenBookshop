package com.greenbookshop.admin.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenbookshop.admin.paging.PagingAndSortingHelper;
import com.greenbookshop.admin.repository.ProvinceRepository;
import com.greenbookshop.admin.repository.ProductRepository;
import com.greenbookshop.admin.repository.ShippingRateRepository;
import com.greenbookshop.admin.service.impl.IShippingRateService;
import com.greenbookshop.common.entity.Province;
import com.greenbookshop.common.entity.ShippingRate;
import com.greenbookshop.common.entity.product.Product;
import com.greenbookshop.common.exception.ShippingRateAlreadyExistsException;
import com.greenbookshop.common.exception.ShippingRateNotFoundException;

@Service
@Transactional
public class ShippingRateService implements IShippingRateService{
	
	public static final int RATES_PER_PAGE = 10;
	private static final int DIM_DIVISOR = 139;	
 
	private ShippingRateRepository shipRepo;
	
	private ProvinceRepository provinceRepo;
	
	private ProductRepository productRepo;
	
	@Autowired
	public ShippingRateService(ShippingRateRepository shipRepo, ProvinceRepository provinceRepo,ProductRepository productRepo) {
		super();
		this.shipRepo = shipRepo;
		this.provinceRepo = provinceRepo;
		this.productRepo = productRepo;
	}

	@Override
	public void listByPage(int pageNum, PagingAndSortingHelper helper) {
		// TODO Auto-generated method stub
		helper.listEntities(pageNum, RATES_PER_PAGE, shipRepo);
	}
	
	@Override
	public List<Province> listAllProvinces() {
		// TODO Auto-generated method stub
		return provinceRepo.findAllByOrderByNameAsc();
	}

	@Override
	public void save(ShippingRate rateInForm) throws ShippingRateAlreadyExistsException {
		// TODO Auto-generated method stub
		ShippingRate rateInDB = shipRepo.findByProvinceAndDistrict(
				rateInForm.getProvince().getId(), rateInForm.getDistrict());
		boolean foundExistingRateInNewMode = rateInForm.getId() == null && rateInDB != null;
		boolean foundDifferentExistingRateInEditMode = rateInForm.getId() != null && rateInDB != null && !rateInDB.equals(rateInForm);

		if (foundExistingRateInNewMode || foundDifferentExistingRateInEditMode) {
			throw new ShippingRateAlreadyExistsException("There's already a rate for the destination "
						+ rateInForm.getProvince().getName() + ", " + rateInForm.getDistrict()); 					
		}
		shipRepo.save(rateInForm);
	}

	@Override
	public ShippingRate get(Integer id) throws ShippingRateNotFoundException {
		// TODO Auto-generated method stub
		try {
			return shipRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new ShippingRateNotFoundException("Could not find shipping rate with ID " + id);
		}
	}

	@Override
	public void updateCODSupport(Integer id, boolean codSupported) throws ShippingRateNotFoundException {
		// TODO Auto-generated method stub
		Long count = shipRepo.countById(id);
		if (count == null || count == 0) {
			throw new ShippingRateNotFoundException("Could not find shipping rate with ID " + id);
		}

		shipRepo.updateCODSupport(id, codSupported);
	}

	@Override
	public void delete(Integer id) throws ShippingRateNotFoundException {
		// TODO Auto-generated method stub
		Long count = shipRepo.countById(id);
		if (count == null || count == 0) {
			throw new ShippingRateNotFoundException("Could not find shipping rate with ID " + id);

		}
		shipRepo.deleteById(id);
	}
	
	public float calculateShippingCost(Integer productId, Integer provinceId, String district) 
			throws ShippingRateNotFoundException {
		ShippingRate shippingRate = shipRepo.findByProvinceAndDistrict(provinceId, district);

		if (shippingRate == null) {
			throw new ShippingRateNotFoundException("No shipping rate found for the given "
					+ "destination. You have to enter shipping cost manually.");
		}

		Product product = productRepo.findById(productId).get();

		float dimWeight = (product.getLength() * product.getWidth() * product.getHeight()) / DIM_DIVISOR;
		float finalWeight = product.getWeight() > dimWeight ? product.getWeight() : dimWeight;

		return finalWeight * shippingRate.getRate();
	}

}
