import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task } from '../models/task';

type Params = {
  search: string,
  activeCategoryId: number, 
  currentPage: number, 
  pageSize: number, 
  sort: string, 
  direction: string
};

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private apiUrl = "http://localhost:8080/api/tasks";

  constructor(private http: HttpClient) {}

  getTasks(params: Params): Observable<any> {
    return this.http.get<any>(this.apiUrl, {params: {
      search: params.search,
      categoryId: params.activeCategoryId,
      page: params.currentPage, 
      size: params.pageSize, 
      sort: params.sort + "," + params.direction}});
  }

  getTask(id: number): Observable<Task> {
    return this.http.get<Task>(this.apiUrl + "/edit/" + id);
  }

  createTask(task: Task): Observable<Task> {
    return this.http.post<Task>(this.apiUrl, task);
  }

  updateTask(id: number, task: Task): Observable<Task> {
    console.log(task);
    return this.http.put<Task>(this.apiUrl + "/edit/" + id , task);
  }

  deleteTask(id: number): Observable<Task[]> {
    return this.http.delete<any[]>(this.apiUrl + "/" + id);
  }
}
