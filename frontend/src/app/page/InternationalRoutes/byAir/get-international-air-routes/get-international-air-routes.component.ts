import { MenuItem, MessageService } from 'primeng/api';
import { InternationalRouteService } from '../../service/international-route.service';
import { Component } from '@angular/core';
import { Routes } from '../../../../model/ShipmentRoutes';
import { InternationalRoutes } from 'src/app/model/InternationalRoute';

@Component({
  selector: 'app-get-international-air-routes',
  templateUrl: './get-international-air-routes.component.html',
  styleUrls: ['./get-international-air-routes.component.scss'],
  providers: [MessageService]
})
export class GetInternationalAirRoutesComponent {

  constructor(private internationalRouteService: InternationalRouteService, private messageService: MessageService) { }

  items: MenuItem[] | undefined;
  internationalRoutes!: InternationalRoutes[];
  visible: boolean = false;
  rID!: number
  first: number = 0;
  rows: number = 10;
  totalRecords: number = 0;
  myApiResponse: any;
  page = 0;
  size = 10;

  value: string = '';
  status:boolean=true

  ngOnInit() {
    this.items = [{ label: 'International Route List For Air' }];
    this.getAllInternationalRoutes(this.value.trim(),this.status,this.page,this.size);
  }

  searchByFilter(){
    this.getAllInternationalRoutes(this.value.trim(),this.status,0,this.size);
  }
  clearFilter(){
    this.value='';
  }

  onPageChange(event: any) {
    this.page = event.page;
    this.rows = event.rows;
    this.getAllInternationalRoutes(this.value.trim(),this.status,this.page,this.rows)
}


  getAllInternationalRoutes(value:string,status:boolean,page:number,size:number) {
    this.internationalRouteService.getAllInternationalRoutesForAir({value:value,type:"Air",status:status},page,size).subscribe((res: any) => {
      this.internationalRoutes = res.content;
      this.myApiResponse = res;
      this.page=res.pageable.pageNumber;
      this.size=res.size;
      this.totalRecords = this.myApiResponse.totalElements;
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
  }

  deleteInternationalRouteByID(id: number) {
    
    this.internationalRouteService.deleteInternationalRoute(id).subscribe((res: any) => {
      
      this.visible = false;
      this.getAllInternationalRoutes(this.value.trim(),this.status,this.page,this.size);
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Route is deleted' });
    
    },(error:any)=>{
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    });
  }

  showDialog(id: number) {
    this.rID = id;
    this.visible = true;
  }

}
interface PageEvent {
  first: number;
  rows: number;
  page: number;
  pageCount: number;
}
