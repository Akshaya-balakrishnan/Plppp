package com.cg.crs.client;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.cg.crs.dao.CRSDao;
import com.cg.crs.dao.CRSDaoImpl;
import com.cg.crs.entity.UserRole;
import com.cg.crs.exception.CRSException;
import com.cg.crs.service.CRSService;
import com.cg.crs.service.CRSServiceImpl;

public class UserProfileCreation {

	public static void profileCreation(UserRole user) throws CRSException, InputMismatchException {
		// TODO Auto-generated method stub
		CRSService crsService = new CRSServiceImpl();
		CRSDao crsDao = new CRSDaoImpl();
		Scanner scanner = new Scanner(System.in);
		boolean flag3 = false;
		int choice = 0;

		if (user.getRoleCode().equals("ADMIN")) {
			do {
				 scanner = new Scanner(System.in);
				System.out.println("Enter your choice");
				System.out.println("1.New Profile Creation");
				System.out.println("2.Claim Creation");
				System.out.println("3.View Claim");
				System.out.println("4.Report Generation");
				try {
					choice = scanner.nextInt();
					if(choice<=4)
					{
					flag3 = true;
					}
					else {
						flag3=false;
						System.err.println("Enter input from 1 to 4");
					}
				} catch (InputMismatchException e) {

					
					System.err.println("Enter digits");
					flag3 = false;
				   
				}
			} while (!flag3);
			String[] args = null;
			switch (choice) {
			case 1:

				UserRole userProfile = new UserRole();
				boolean flag2 = false;
				do {
					
					System.out.println("Enter new Username");
					scanner.nextLine();
					String userName = scanner.nextLine();
					System.out.println("Enter new password");
					String password = scanner.nextLine();
					System.out.println("Enter new role code from the following list");
					System.out.println("1.Insured\n2.Agent\n3.Admin");

					boolean flag = false;
					do {
						scanner = new Scanner(System.in);
						int roleCode=0;
						
						try {
							roleCode = scanner.nextInt();
							if(roleCode<4)
						{
								flag=true;
							}
							else {
								flag=false;
								System.err.println("Enter input from 1 to 4");
							}
							
							
						} catch (InputMismatchException e) {
							
							System.err.println("Enter valid digits");
							flag=false;
						}
						
						switch (roleCode) {
						case 1:
							userProfile.setRoleCode("INSURED");
							flag = true;
							break;
						case 2:
							userProfile.setRoleCode("AGENT");
							flag = true;
							break;
						case 3:
							userProfile.setRoleCode("ADMIN");
							flag = true;
							break;
						default:
                            flag=false;
							break;
						}

					} while (!flag);
					userProfile.setUserName(userName);
					userProfile.setPassword(password);

					try {
						if (crsService.profileCreation(userProfile)) {
							flag2 = true;
							System.out.println("Profile Created Successfully");
						} else {
							flag2 = false;
							System.out.println("User already existed");
						}
					} catch (CRSException e) {
						// TODO Auto-generated catch block
						System.err.println("" + e.getMessage());

					}
				} while (!flag2);
			case 2:
				ClaimCreation.main(args);break;
			case 3:ClaimDetails.main(args);break;
			case 4:
				ReportGeneration.reportGeneration(user);
				break;
			default:
				break;
			}
			
		}

	}
}
