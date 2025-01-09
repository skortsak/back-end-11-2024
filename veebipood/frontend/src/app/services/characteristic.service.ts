import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Characteristic } from '../models/Characteristic';
import { Category } from '../models/Category';

@Injectable({
  providedIn: 'root'
})
export class CharacteristicService {
  private url = "http://localhost:8080/characteristics";

  constructor(private http: HttpClient) { }

  getCharacteristics(): Observable<Characteristic[]> {
    return this.http.get<Characteristic[]>(this.url);
  }

  addCharacteristics(newCharacteristic: String): Observable<Characteristic[]> {
    return this.http.post<any[]>(this.url, {"name": newCharacteristic},
      {headers: {"Authorization": "Bearer " + sessionStorage.getItem("token") || ""}}
    );
  }

  removeCharacteristics(id: number): Observable<Characteristic[]> {
    return this.http.delete<any[]>(this.url +"/" + id,
      {headers: {"Authorization": "Bearer " + sessionStorage.getItem("token") || ""}}
    );
  }
}
