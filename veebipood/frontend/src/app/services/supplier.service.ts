import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SupplierService {
  private url = "http://localhost:8080";

  constructor(private http: HttpClient) { }

    getSupplierProducts() {
      return this.http.get(this.url + "/store-products",
        {headers: {"Authorization": "Bearer " + sessionStorage.getItem("token") || ""}}
      );
    }

    getEscuelaProducts() {
      return this.http.get(this.url + "/escuela-products",
        {headers: {"Authorization": "Bearer " + sessionStorage.getItem("token") || ""}}
      );
    }

    getBooks(search: string) {
      return this.http.get(this.url + "/books", {params: {search},
        headers: {"Authorization": "Bearer " + sessionStorage.getItem("token") || ""}});
    }
  }
