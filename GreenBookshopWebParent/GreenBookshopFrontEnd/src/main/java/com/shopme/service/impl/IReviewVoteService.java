package com.greenbookshop.service.impl;

import com.greenbookshop.common.entity.Customer;
import com.greenbookshop.common.entity.ReviewVote;
import com.greenbookshop.common.entity.VoteResultDTO;
import com.greenbookshop.common.entity.VoteType;

public interface IReviewVoteService {

	public VoteResultDTO undoVote(ReviewVote vote, Integer reviewId, VoteType voteType);
	public VoteResultDTO doVote(Integer reviewId, Customer customer, VoteType voteType);
	
}
