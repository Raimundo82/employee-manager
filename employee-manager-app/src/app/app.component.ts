import {Component, OnInit} from '@angular/core';
import {Employee} from './employee';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {EmployeeService} from './employee.service';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  employees: Employee[] | undefined;
  editEmployee: Employee | null = null;
  deleteEmployee: Employee | null = null;

  constructor(private employeeService: EmployeeService) {
  }

  ngOnInit(): void {
    this.getEmployees();
  }

  getEmployees(): void {
    this.employeeService.getEmployees().subscribe(
      (response: Employee[]) => {
        this.employees = response;
      },
      (e: HttpErrorResponse) => {
        alert(e.error.message);
      }
    );
  }

  searchEmployee(key: string): void {
    const results: Employee[] = [];
    // @ts-ignore
    for (const employee of this.employees) {
      console.log(key.toLowerCase());
      if (employee.name.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results.push(employee);
      }
      this.employees = results;
      if (results.length === 0 || !key) {
        this.getEmployees();
      }
    }
  }

  onAddEmployee(addForm: NgForm): void {
    // @ts-ignore
    this.employeeService.addEmployee(addForm.value).subscribe(
      (response: Employee) => {
        // @ts-ignore
        document.getElementById('add-employee-form').click();
        this.getEmployees();
        addForm.reset();
      },
      (e: HttpErrorResponse) => {
        alert(e.error.message);
      }
    );
  }

  onUpdateEmployee(employee: Employee): void {
    this.employeeService.updateEmployee(employee).subscribe(
      (response: Employee) => {
        console.log(response);
        this.getEmployees();
      },
      (e: HttpErrorResponse) => {
        alert(e.error.message);
      }
    );
  }

  onDeleteEmployee(id: number | undefined): void {
    this.employeeService.deleteEmployee(id as number).subscribe(
      (response) => {
        console.log(response),
          this.getEmployees();
      },
      (e: HttpErrorResponse) => {
        alert(e.error.message);
      }
    );
  }

  public onOpenModal(employee: Employee, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addEmployeeModal');
    }
    if (mode === 'edit') {
      this.editEmployee = employee;
      button.setAttribute('data-target', '#updateEmployeeModal');
    }
    if (mode === 'delete') {
      this.deleteEmployee = employee;
      button.setAttribute('data-target', '#deleteEmployeeModal');
    }
    container?.appendChild(button);
    button.click();
  }


  getJobTitle(jobTitle: string | undefined): string | undefined {
    switch (jobTitle) {
      case 'PROJECT_MANAGER':
        return 'Project Manager';
      case 'DESIGNER':
        return 'Designer';
      case 'DEVELOPER':
        return 'Developer';
      default:
        return jobTitle;
    }
  }
}
