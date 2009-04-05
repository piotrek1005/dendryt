package ntbd.employee;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Employee implements Comparable<Employee>
{
	private String name;
	private Date hiredate;
	private String job;
	private double salary;
	private Department department;
	private Set projects;

	public Department getDepartment()
	{
		return department;
	}
	
	public int compareTo(Employee emp)
	{
		if(salary<emp.getSalary())
			return -1;
		else
			if(salary>emp.getSalary())
				return 1;
			else
				return 0;
	}

	public Date getHiredate()
	{
		return hiredate;
	}

	public String getJob()
	{
		return job;
	}

	public String getName()
	{
		return name;
	}

	public double getSalary()
	{
		return salary;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

	public void setHiredate(Date date)
	{
		hiredate = date;
	}

	public void setJob(String string)
	{
		job = string;
	}

	public void setName(String string)
	{
		name = string;
	}

	public void setSalary(double d)
	{
		salary = d;
	}

	public Set getProjects()
	{
		if( projects == null )
		{
			projects = new HashSet();
		}
		return projects;
	}

	public void addProject(Project project)
	{
		getProjects().add(project);
	}

	public void removeProject(Project project)
	{
		getProjects().remove(project);
	}

	public String toString()
	{
		return " [NAME] "
			+ name
			+ " [DEPARTMENT] "
			+ department
			+ " [HIREDATE] "
			+ hiredate
			+ " [JOB] "
			+ job
			+ " [SALARY] "
			+ salary;
	}
}
