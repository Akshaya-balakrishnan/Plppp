package com.cg.crs.dao;

import java.util.List;

import com.cg.crs.entity.ClaimCreationEntity;
import com.cg.crs.entity.ClaimDetailsEntity;
import com.cg.crs.entity.PolicyEntity;
import com.cg.crs.entity.Report;
import com.cg.crs.entity.UserRole;
import com.cg.crs.exception.CRSException;

public interface CRSDao {

	

	boolean validateFields(UserRole user) throws CRSException;

	boolean profileCreation(UserRole userProfile)throws CRSException;

	List<Report> reportGeneration(String userName) throws CRSException;

	List<Report> detailedView(Report report) throws CRSException;

	Boolean userExists(String userName) throws CRSException;
	
	int insertClaimDetails(ClaimCreationEntity claimCreation) throws CRSException;

	List<ClaimDetailsEntity> getQuestions(long policyNumber) throws CRSException;

	int insertQuestions(long policyNumber, String claimQuesId, String claimAns)  throws CRSException;

	List<PolicyEntity> getPolicies() throws CRSException;

	List<ClaimCreationEntity> viewClaimStatus(long claimPolicyNo) throws CRSException;;
	

}
