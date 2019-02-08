package com.cg.crs.client;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.cg.crs.entity.ClaimCreationEntity;
import com.cg.crs.entity.PolicyEntity;
import com.cg.crs.entity.UserRole;
import com.cg.crs.exception.CRSException;
import com.cg.crs.service.CRSService;
import com.cg.crs.service.CRSServiceImpl;

public class ClaimCreation {
	static Logger logger = Logger.getLogger(ClaimCreation.class);
	static long policyNumber = 0;

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		PropertyConfigurator.configure("resources/log4j.properties");
		Scanner scanner = null;
		int choice = 0;
		UserRole user=new UserRole();
		boolean choiceFlag = false;
		CRSService service = new CRSServiceImpl();
		System.out.println("******** Insured Insurance Claiming **********");
		System.out.println(" ");
		List<PolicyEntity> list;
		try {
			list = service.getPolicies();
			System.out.println("********** Policy List **********");
			for (PolicyEntity entity : list) {
				System.out.println("Policy Number   Premium Amount  Account Number");
				System.out.println(entity.getPolicyNumber()  + "      " + 
								   entity.getPolicyPremium() + "           " +
								   entity.getAccountNumber());
				System.out.println(" ");

			}
		} catch (CRSException e1) {
			System.err.println("Problem Occured while Viewing Policies");
		}

		do {
			scanner = new Scanner(System.in);
			if (!user.getRoleCode().equals("ADMIN")) {
			System.out.println("1. Claim Creation");
			System.out.println("2. View the claim status");
			}
			System.out.println("Enter choice");
			try {
				choice = scanner.nextInt();
				choiceFlag = true;
				switch (choice) {
				case 1:
					/* scanner.nextLine(); */

					String claimReason;
					boolean doClaimReason = false;
					boolean validateClaimReason;
					do {
						scanner = new Scanner(System.in);
						System.out.println("Enter the reason of the claim: ");
						claimReason = scanner.nextLine();
						validateClaimReason = service.CheckClaimReason(claimReason);
						if (validateClaimReason == false) {
							System.err.println("The reason should always starts with uppercase");
							doClaimReason = false;
						} else {
							doClaimReason = true;
						}
					} while (doClaimReason == false);

					if (validateClaimReason) {
						String accidentLocationStreet;
						boolean validateAccidentLocationStreet;
						boolean doAccidentLocationStreet = false;
						do {
							scanner = new Scanner(System.in);
							System.out.println("Enter the street of the accident: ");
							accidentLocationStreet = scanner.nextLine();
							validateAccidentLocationStreet = service
									.CheckAccidentLocationStreet(accidentLocationStreet);
							if (validateAccidentLocationStreet == false) {
								System.err.println("The Street name should always starts with uppercase");
								doAccidentLocationStreet = false;

							} else {
								doAccidentLocationStreet = true;
							}

						} while (doAccidentLocationStreet == false);

						if (validateAccidentLocationStreet) {
							String accidentCity;
							boolean validateAccidentCity;
							boolean doAccidentCity = false;
							do {
								scanner = new Scanner(System.in);
								System.out.println("Enter the city where accident occurred: ");
								accidentCity = scanner.nextLine();
								validateAccidentCity = service.CheckAccidentCity(accidentCity);
								if (validateAccidentCity == false) {
									System.err.println("The City name should always starts with uppercase");
									doAccidentCity = false;

								} else {
									doAccidentCity = true;
								}

							} while (doAccidentCity == false);
							if (validateAccidentCity) {
								String accidentState;
								boolean validateAccidentState;
								boolean doAccidentState = false;
								do {
									scanner = new Scanner(System.in);
									System.out.println("Enter the state where accident occurred: ");
									accidentState = scanner.nextLine();
									validateAccidentState = service.CheckAccidentState(accidentState);
									if (validateAccidentState == false) {
										System.err.println("The State should always starts with uppercase");
										doAccidentState = false;

									} else {
										doAccidentState = true;
									}

								} while (doAccidentState == false);
								if (validateAccidentState) {
									long accidentZip = 0;
									boolean validateAccidentZip = false;
									boolean doAccidentZip = false;
									do {
										scanner = new Scanner(System.in);
										System.out.println("Enter the postal code where accident occurred: ");
										try {
											accidentZip = scanner.nextLong();
											validateAccidentZip = service.CheckAccidentZip(accidentZip);
											if (validateAccidentZip == false) {
												System.err
														.println("The Postal code must be only digits with 5 numbers");
												doAccidentZip = false;

											} else {
												doAccidentZip = true;
											}
										} catch (InputMismatchException e) {
											System.err.println("Enter Only Digits");

										}

									} while (doAccidentZip == false);

									if (validateAccidentZip) {
										boolean selectClaimTypeFlag = false;
										String claimType = " ";
										do {
											scanner = new Scanner(System.in);
											System.out.println("Select the type which you want to claim: ");
											System.out.println("1. Annum ");
											System.out.println("2. Half Yearly");
											System.out.println("3. Quaterly");
											try {

												int selectClaimType = scanner.nextInt();
												selectClaimTypeFlag = true;
												switch (selectClaimType) {
												case 1:
													claimType = "Annum";
													break;
												case 2:
													claimType = "Half Yearly";
													break;
												case 3:

													claimType = "Quaterly";
													break;
												default:
													selectClaimTypeFlag = false;
													System.err.println("Select any options from 1 to 3");
													break;
												}
											} catch (InputMismatchException e) {
												System.err.println("Enter the valid details of integer type");
											}
										} while (!selectClaimTypeFlag);

										boolean doPolicyNumberFlag = false;
										boolean validatePolicyNumber;

										do {
											scanner = new Scanner(System.in);
											System.out.println("Enter the policy number of your policy: ");

											try {
												policyNumber = scanner.nextLong();
												validatePolicyNumber = service.CheckPolicyNumber(policyNumber);
												if (validatePolicyNumber == false) {
													System.err.println("Enter the policy number of 10 digits");
													doPolicyNumberFlag = false;
												} else {
													doPolicyNumberFlag = true;
												}
											} catch (InputMismatchException e) {
												System.err.println("Enter only digits");
											}
										} while (!doPolicyNumberFlag);

										ClaimCreationEntity claimCreation = new ClaimCreationEntity();
										claimCreation.setClaimReason(claimReason);
										claimCreation.setAccidentLocationStreet(accidentLocationStreet);
										claimCreation.setAccidentCity(accidentCity);
										claimCreation.setAccidentState(accidentState);
										claimCreation.setAccidentZip(accidentZip);
										claimCreation.setClaimType(claimType);
										claimCreation.setPolicyNumber(policyNumber);

										try {
											service.insertClaimDetails(claimCreation);
											
											scanner.nextLine();
											boolean doYesNo = false;
											do {
												scanner = new Scanner(System.in);
												System.out.println("Enter Yes or No to enter Claim Details Screen");
												String claimDetails = scanner.nextLine();
												if (claimDetails.equals("Yes") || claimDetails.equals("yes")
														|| claimDetails.equals("Y") || claimDetails.equals("y")) {
													doYesNo=true;
													ClaimDetails.main(args);
												} else if (claimDetails.equals("No") || claimDetails.equals("no")
														|| claimDetails.equals("n") || claimDetails.equals("N")) {
													System.out.println("Thank You");
												} else {
													System.out.println("Enter only Yes or No or Y/N");
												}
											} while (!doYesNo);

										} catch (CRSException e) {
											e.printStackTrace();
											System.err.println("Problem Occured while Claim Creation");

										}
									}
								}
							}
						}
					}
					break;

				case 2:
					System.out.println("Enter Policy Number for viewing Claim Status");
					long claimPolicyNo = scanner.nextLong();
					List<ClaimCreationEntity> list2;
					try {
						list2 = service.viewClaimStatus(claimPolicyNo);
						System.out.println("Your Claims");
						for (ClaimCreationEntity entity : list2) {
							System.out.println("\nClaim Number:    " + entity.getClaimNumber() + "\nClaim Reason:    "
									+ entity.getClaimReason() + "\nAccident Street: "
									+ entity.getAccidentLocationStreet() + "\nAccident City:   "
									+ entity.getAccidentCity() + "\nAccident State   " + entity.getAccidentState()
									+ "\nAccident Zip     " + entity.getAccidentZip() + "\nClaim Type       "
									+ entity.getClaimType() + "\nYour Claim is Approved ");
						}
					} catch (CRSException e) {
						System.err.println("Problem Occured while Viewing Claim Status");
					}

					break;
				default:
					choiceFlag = false;
					System.out.println("input should be in the range of (1-4)");
					System.err.println("Enter your input again");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Enter the valid details");
				System.err.println("Enter you input again");

			}

		} while (!choiceFlag);

		/* scanner.close(); */

	}
}
