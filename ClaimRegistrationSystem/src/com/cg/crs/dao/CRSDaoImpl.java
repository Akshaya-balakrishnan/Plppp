package com.cg.crs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cg.crs.entity.ClaimCreationEntity;
import com.cg.crs.entity.ClaimDetailsEntity;
import com.cg.crs.entity.PolicyEntity;
import com.cg.crs.entity.Report;
import com.cg.crs.entity.UserRole;
import com.cg.crs.exception.CRSException;
import com.cg.crs.utility.JDBCUtility;

public class CRSDaoImpl implements CRSDao {
	Connection connection = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	static Logger logger = Logger.getLogger(CRSDaoImpl.class);
	

	@Override
	public boolean validateFields(UserRole user) throws CRSException {
		// TODO Auto-generated method stub
			List<UserRole> list = new ArrayList<>();
			connection = JDBCUtility.getConnection();
			boolean flag = false;
			try {
				statement = connection.prepareStatement(QueryMapper.checkUser);
				statement.setString(1, user.getUserName());
				statement.setString(2, user.getPassword());
				resultSet = statement.executeQuery();

				while (resultSet.next()) {
					String roleCode = resultSet.getString(1);
					user.setRoleCode(roleCode);
					list.add(user);
				}
				
				if (!list.isEmpty()) {
					flag = true;
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new CRSException("Not valid");
			}
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new CRSException("Result set is not closed");

			}
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new CRSException("Statement is not closed");

			}
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new CRSException("Connection is not closed");

			}
			return flag;

	}



	@Override
	public boolean profileCreation(UserRole userProfile) throws CRSException {
		// TODO Auto-generated method stub
		connection = JDBCUtility.getConnection();
		boolean flag = false;
		int value=0;
		String userName="";
		try {
			statement = connection.prepareStatement(QueryMapper.userProfileCheck);
			statement.setString(1, userProfile.getUserName());
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
			userName = resultSet.getString(1);
			}
				if(!userProfile.getUserName().equals(userName))
				{
					statement = connection.prepareStatement(QueryMapper.userProfileCreation);
					statement.setString(1, userProfile.getUserName());
					statement.setString(2, userProfile.getPassword());
					statement.setString(3, userProfile.getRoleCode());
					value = statement.executeUpdate();
				}
         if(value!=0)
         {
        	 flag=true;
         }
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CRSException("Not valid");
		}
		try {
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CRSException("Result set is not closed");

		}
		try {
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CRSException("Statement is not closed");

		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CRSException("Connection is not closed");

		}
		return flag;

		
	}



	@Override
	public List<Report> reportGeneration(String userName) throws CRSException {
		// TODO Auto-generated method stub
		connection = JDBCUtility.getConnection();
		boolean flag = false;
		
		int value=0;
		String userName1="";
        Report report=new Report();
        UserRole user=new UserRole();
        List<Report> list=new ArrayList<>();
		try {
			statement = connection.prepareStatement(QueryMapper.userProfileCheck);
			statement.setString(1, userName);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
			userName1 = resultSet.getString(1);
			user.setUserName(userName1);
			}
				if(userName1.equals(userName))
				{
					statement=connection.prepareStatement(QueryMapper.reportGeneration);
					statement.setString(1, userName);
					resultSet=statement.executeQuery();
					
					while(resultSet.next())
					{
						 Long policyNumber=resultSet.getLong(1);
						 Long claimNumber=resultSet.getLong(2);
						 String claimType=resultSet.getString(3);
						 
						 report.setPolicyNumber(policyNumber);
						 report.setClaimNumber(claimNumber);
						 report.setClaimType(claimType);
						 
						 
						 list.add(report);
					}
				}
				else
				{
					System.err.println("User doesn't exists,enter valid userName");
				}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CRSException("Not valid");
		}
		try {
			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CRSException("Result set is not closed");

		}
		try {
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CRSException("Statement is not closed");

		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new CRSException("Connection is not closed");

		}
		
		return list;
	}



	@Override
	public List<Report> detailedView(Report report) throws CRSException {
		// TODO Auto-generated method stub
		List<Report> list = new ArrayList<>();
		connection = JDBCUtility.getConnection();
		boolean flag = false;
		
			try {
				statement = connection.prepareStatement(QueryMapper.detailedView);
				statement.setLong(1, report.getPolicyNumber());
				resultSet=statement.executeQuery();
				
				while(resultSet.next())
				{
				
					String claimReason=resultSet.getString(1);
					String accidentStreet=resultSet.getString(2);
					String accidentCity=resultSet.getString(3);	
					String accidentState=resultSet.getString(4);
					Integer accidentZip=resultSet.getInt(5);
					String claimType=resultSet.getString(6);
					String claimQuesDesc1=resultSet.getString(7); 
					String claimQuesAns1=resultSet.getString(8);                
					Report report1=new Report();
					report1.setClaimReason(claimReason);
					report1.setAccidentStreet(accidentStreet);
					report1.setAccidentCity(accidentCity);
					report1.setAccidentState(accidentState);
					report1.setAccidentZip(accidentZip);
					report1.setClaimType(claimType);
					report1.setClaimQuesDesc1(claimQuesDesc1);
					report1.setClaimQuesAns1(claimQuesAns1);
					
					list.add(report1);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			  throw new CRSException("Not retreived");
			}
			
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new CRSException("Result set is not closed");

			}
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new CRSException("Statement is not closed");

			}
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new CRSException("Connection is not closed");

			}
			
			return list;
	
		}



	@Override
	public Boolean userExists(String userName) throws CRSException {
		// TODO Auto-generated method stub
		connection = JDBCUtility.getConnection();
		boolean flag = false;
		String userName1="";
        UserRole user=new UserRole();
        List<Report> list=new ArrayList<>();
		try {
			statement = connection.prepareStatement(QueryMapper.userProfileCheck);
			statement.setString(1, userName);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
			userName1 = resultSet.getString(1);
			user.setUserName(userName1);
			}
				if(userName1.equals(userName))
				{
					flag=true;
				}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			  throw new CRSException("Invalid");
			}
			
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new CRSException("Result set is not closed");

			}
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new CRSException("Statement is not closed");

			}
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new CRSException("Connection is not closed");

			}
			
		
	
				return flag;
		}
	
	@Override
	public int insertClaimDetails(ClaimCreationEntity claimCreation) throws CRSException {
		logger.info("in add Customer method");
		connection = JDBCUtility.getConnection();
		logger.info("connection object created");
		int generatedId = 0;
		try {
			statement = connection.prepareStatement(QueryMapper.insertCustomerDetails);
			logger.debug("statement object created");
			statement.setString(1, claimCreation.getClaimReason());
			statement.setString(2, claimCreation.getAccidentLocationStreet());
			statement.setString(3, claimCreation.getAccidentCity());
			statement.setString(4, claimCreation.getAccidentState());
			statement.setLong(5, claimCreation.getAccidentZip());
			statement.setString(6, claimCreation.getClaimType());
			statement.setLong(7, claimCreation.getPolicyNumber());
			generatedId = statement.executeUpdate();
			logger.info("execute update called");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new CRSException("problem occured while creating the insertClaimDetails statement object");
		} finally {
			try {
				statement.close();
				} catch (SQLException e) {
					throw new CRSException("Statament Not Closed");
					}
			try {
				connection.close();
				} catch (SQLException e) {
					throw new CRSException("Connection Not Closed");
					}
			}
		return generatedId;
	}

	@Override
	public List<ClaimDetailsEntity> getQuestions(long policyNumber) throws CRSException {
		// TODO Auto-generated method stub
		List<ClaimDetailsEntity> list = new ArrayList<>();
		connection = JDBCUtility.getConnection();

		try {
			statement = connection.prepareStatement(QueryMapper.getClaimQuestionId);
			statement.setLong(1, policyNumber);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String claimQuesId = resultSet.getString(1);
				int claimQuesSeq = resultSet.getInt(2);
				String busSeqId = resultSet.getString(3);
				String claimQuesDesc = resultSet.getString(4);
				String claimQuesAns1 = resultSet.getString(5);
				int claimQues1Weightage = resultSet.getInt(6);
				String claimQuesAns2 = resultSet.getString(7);
				int claimQues2Weightage = resultSet.getInt(8);
				String claimQuesAns3 = resultSet.getString(9);
				int claimQues3Weightage = resultSet.getInt(10);
				String claimQuesAns4 = resultSet.getString(11);
				int claimQues4Weightage = resultSet.getInt(12);
				ClaimDetailsEntity claimDetailsEntity = new ClaimDetailsEntity();
				claimDetailsEntity.setClaimQuesId(claimQuesId);
				claimDetailsEntity.setClaimQuesSeq(claimQuesSeq);
				claimDetailsEntity.setBusSeqId(busSeqId);
				claimDetailsEntity.setClaimQuesDesc(claimQuesDesc);
				claimDetailsEntity.setClaimQuesAns1(claimQuesAns1);
				claimDetailsEntity.setClaimQuesAns1Weightage(claimQues1Weightage);
				claimDetailsEntity.setClaimQuesAns2(claimQuesAns2);
				claimDetailsEntity.setClaimQuesAns2Weightage(claimQues2Weightage);
				claimDetailsEntity.setClaimQuesAns3(claimQuesAns3);
				claimDetailsEntity.setClaimQuesAns3Weightage(claimQues3Weightage);
				claimDetailsEntity.setClaimQuesAns4(claimQuesAns4);
				claimDetailsEntity.setClaimQuesAns4Weightage(claimQues4Weightage);
				list.add(claimDetailsEntity);
			}

		} catch (SQLException e) {
			throw new CRSException("problem occured while creating the getQuestions statement object");
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				throw new CRSException("Result Set Not Closed");
			}
			try {
				statement.close();
			} catch (SQLException e) {
				throw new CRSException("Statament Not Closed");
			}
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CRSException("Connection Not Closed");
			}
		}

		return list;
	}

	@Override
	public int insertQuestions(long policyNumber, String claimQuesId, String claimAns) throws CRSException {
		// TODO Auto-generated method stub
		connection = JDBCUtility.getConnection();
		int rows = 0;
		try {
			statement = connection.prepareStatement(QueryMapper.insertQuestion);
			statement.setLong(1, policyNumber);
			statement.setString(2, claimQuesId);
			statement.setString(3, claimAns);
			rows = statement.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new CRSException("problem occured while creating the insertQuestions statement object");
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				throw new CRSException("Statament Not Closed");
			}
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CRSException("Connection Not Closed");
			}
		}

		return rows;
	}

	@Override
	public List<PolicyEntity> getPolicies() throws CRSException {
		// TODO Auto-generated method stub
		List<PolicyEntity> list = new ArrayList<>();
		connection = JDBCUtility.getConnection();
		try {
			statement = connection.prepareStatement(QueryMapper.getPolicies);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				long policyNumber = resultSet.getLong(1);
				double policyPremium = resultSet.getDouble(2);
				long accountNumber = resultSet.getLong(3);
				
				PolicyEntity policy = new PolicyEntity();
				policy.setPolicyNumber(policyNumber);
				policy.setPolicyPremium(policyPremium);
				policy.setAccountNumber(accountNumber);
				list.add(policy);

			}
		} catch (SQLException e) {
			throw new CRSException("problem occured while creating the getPolicies statement object");
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				throw new CRSException("Result Set Not Closed");
			}
			try {
				statement.close();
			} catch (SQLException e) {
				throw new CRSException("Statament Not Closed");
			}
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CRSException("Connection Not Closed");
			}
		}
		return list;
	}

	@Override
	public List<ClaimCreationEntity> viewClaimStatus(long claimPolicyNo) throws CRSException {
		List<ClaimCreationEntity> list = new ArrayList<>();
		connection = JDBCUtility.getConnection();
		try {
			statement = connection.prepareStatement(QueryMapper.getClaimStatus);
			statement.setLong(1, claimPolicyNo);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Long claimNumber = resultSet.getLong(1);
				String claimReason = resultSet.getString(2);
				String accidentLocationStreet = resultSet.getString(3);
				String accidentCity = resultSet.getString(4);
				String accidentState = resultSet.getString(5);
				Long accidentZip = resultSet.getLong(6);
				String claimType = resultSet.getString(7);
				

				ClaimCreationEntity entity = new ClaimCreationEntity();

				entity.setClaimNumber(claimNumber);
				entity.setClaimReason(claimReason);
				entity.setAccidentLocationStreet(accidentLocationStreet);
				entity.setAccidentCity(accidentCity);
				entity.setAccidentState(accidentState);
				entity.setAccidentZip(accidentZip);
				entity.setClaimType(claimType);
				list.add(entity);

			}

		} catch (SQLException e) {
			throw new CRSException("problem occured while creating the viewClaimStatus statement object");
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				throw new CRSException("Result Set Not Closed");
			}
			try {
				statement.close();
			} catch (SQLException e) {
				throw new CRSException("Statament Not Closed");
			}
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CRSException("Connection Not Closed");
			}
		}

		return list;

	}


}