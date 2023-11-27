import { InternationalRouteService } from './../../service/international-route.service';
import { MenuItem, MessageService } from 'primeng/api';
import { Routes } from '../../../../model/ShipmentRoutes';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { InternationalRoutes } from 'src/app/model/InternationalRoute';
import { LocationService } from 'src/app/page/location/service/location.service';
import { DatePipe } from '@angular/common';
import { LocationPort } from 'src/app/model/LocationPort';
import { Location } from '../../../../model/Location';
import { LocationPortService } from 'src/app/page/location-port/service/location-port.service';


@Component({
  selector: 'app-add-international-road-routes',
  templateUrl: './add-international-road-routes.component.html',
  styleUrls: ['./add-international-road-routes.component.scss'],
  providers: [MessageService, DatePipe]
})
export class AddInternationalRoadRoutesComponent {


  items: MenuItem[] | undefined;
  type!:Type[];
  internationalRoute: InternationalRoutes = {
    id: null,
    destination: null,
    driverId: null,
    flight: null,
    type: null,
    eta: null,
    etd: null,
    origin: null,
    route: null,
  }

  location!: LocationPort[];



  routeNumbers: any;
  minETDDate: Date = new Date();
  destination!: LocationPort[];

  constructor(
    private internationalRouteService: InternationalRouteService,
    private locationPortService: LocationPortService,
    private messageService: MessageService,
    private datePipe: DatePipe,
    private router: Router) { }


  ngOnInit(): void {
    this.items = [{ label: 'International Route List For Road', routerLink: '/international-routes-for-road' }, { label: 'Add International Route For Road' }];
    this.getInternationalLocations();
    this.type=[{
      type:'Air'
    },{
      type:'Road'
    }]
  }

  getInternationalLocations() {
    this.locationPortService.getAllLocationPort().subscribe((res: LocationPort[]) => {
      this.location = res.filter(el => el.status);
    }, error => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }

  onSubmit() {
    this.internationalRoute.etd = this.datePipe.transform(this.internationalRoute.etd, 'HH:mm:ss')
    this.internationalRoute.eta = this.datePipe.transform(this.internationalRoute.eta, 'HH:mm:ss')
    this.addInternationalRoutes(this.internationalRoute);
  }

  addInternationalRoutes(internationalRoute: InternationalRoutes) {
    debugger
    this.internationalRouteService.addInternationalRoute(internationalRoute).subscribe((res: InternationalRoutes) => {
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'International Route For Air Added Successfully' });
      setTimeout(() => {
        this.router.navigate(['/international-routes-for-road']);
      }, 800);
    }, (error: any) => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }

  onETDDateSelected(selectedETDDate: Date) {
    const minETDDate = new Date(selectedETDDate);

    minETDDate.setDate(minETDDate.getDate() + 1);

    this.minETDDate = minETDDate;
  }

}

interface Type{
  type:string;
}
