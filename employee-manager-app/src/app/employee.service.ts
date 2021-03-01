import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Employee} from './employee';
import {environment} from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {
  }

  public getEmployees(): Observable<Employee[]> {
    return this.http.get<any>(`${this.apiServerUrl}/employees/all`);
  }

  public addEmployee(employee: Employee): Observable<Employee> {
    return this.http.post<any>(`${this.apiServerUrl}/employees/add`, employee);
  }

  public updateEmployee(id: number, employee: Employee): Observable<Employee> {
    return this.http.put<any>(`${this.apiServerUrl}/employees/update/${id}`, employee);
  }

  public deleteEmployee(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/employees/delete/${id}`);
  }
}
