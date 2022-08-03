import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {DatatablesResponse} from "@/entites/datatables-response";
import {HttpParams} from "@angular/common/http";
import {RestApiService} from "@services/rest-api.service";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private url = environment.globalUrl.userApi;

  constructor(private restApi: RestApiService) { }

  getByActiveForDatatable(tableQuery: any, isActive: boolean): Observable<DatatablesResponse> {
    const params = new HttpParams().set("isActive", isActive.toString());
    return this.restApi.post(this.url + "/datatables", tableQuery, params);
  }
}
