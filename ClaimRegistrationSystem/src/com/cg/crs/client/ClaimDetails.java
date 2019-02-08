package com.cg.crs.client;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.cg.crs.entity.ClaimDetailsEntity;
import com.cg.crs.exception.CRSException;
import com.cg.crs.service.CRSService;
import com.cg.crs.service.CRSServiceImpl;

public class ClaimDetails {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		List<ClaimDetailsEntity> list;
		long policyNumber = ClaimCreation.policyNumber;
		CRSService service = new CRSServiceImpl();

		try {
			list = service.getQuestions(policyNumber);
			String claimAns = null;
			String claimQuesId = null;
			int claimAnsWeightage = 0;
			for (ClaimDetailsEntity entity : list) {
				boolean questionFlag = false;
				do {
					scanner = new Scanner(System.in);
					System.out.println(entity.getClaimQuesDesc());
					System.out.println("Select any one of the 4 options");
					System.out.println("1." + entity.getClaimQuesAns1());
					System.out.println("2." + entity.getClaimQuesAns2());
					System.out.println("3." + entity.getClaimQuesAns3());
					System.out.println("4." + entity.getClaimQuesAns4());
					claimQuesId = entity.getClaimQuesId();
					try {
						int choice = scanner.nextInt();
						questionFlag = true;

						switch (choice) {
						case 1:
							claimAns = entity.getClaimQuesAns1();
							claimAnsWeightage = entity.getClaimQuesAns1Weightage();
							break;
						case 2:
							claimAns = entity.getClaimQuesAns2();
							claimAnsWeightage = entity.getClaimQuesAns2Weightage();
							break;
						case 3:
							claimAns = entity.getClaimQuesAns3();
							claimAnsWeightage = entity.getClaimQuesAns3Weightage();
							break;
						case 4:
							claimAns = entity.getClaimQuesAns4();
							claimAnsWeightage = entity.getClaimQuesAns4Weightage();
							break;

						default:
							questionFlag=false;
							System.err.println("Select any options from 1 to 4");
							break;
						}
					} catch (InputMismatchException e) {
						System.err.println("Enter Only Digits");

					}

				} while (!questionFlag);

				System.out.println("You have selected " + claimAns);
				System.out.println(claimAnsWeightage);
				int rows = service.insertQuestions(policyNumber, claimQuesId, claimAns);
				System.out.println(rows + " Question Inserted");
				
			}
			System.out.println("You Have Successfully Applied for Claim");
			System.out.println("Thank You for Applying");
		} catch (CRSException e) {
			System.err.println("Problem Occured while fetching/asking Questions");
		}

	}

}
