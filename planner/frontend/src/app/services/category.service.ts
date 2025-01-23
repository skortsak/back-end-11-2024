import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Category } from '../models/category';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private url = "http://localhost:8080/categories";

  constructor(private http: HttpClient) { }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.url);
  }

  addCategory(newCategory: String): Observable<Category[]> {
    return this.http.post<Category[]>(this.url, {"name": newCategory});
  } 

  deleteCategory(id: number): Observable<Category[]> {
    return this.http.delete<any[]>(this.url + "/" + id);
  }
}
