import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { DashboardService } from './dashboard.service';
import { CardsData } from 'src/app/model/CardData';
import { UserService } from 'src/app/page/user/service/user.service';
import { User } from 'src/app/model/User';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

    chartData: any;
    chartOptions: any;
    basicData: any;

    locationDomOutData: any;
    locationDomInData: any;
    locationIntAirOutData: any;
    locationIntAirInData: any;
    locationIntRoadOutData: any;
    locationIntRoadInData: any;
    DomOutLocationToLocation: any;
    IntAirOutLocationToLocation: any;
    IntRoadOutLocationToLocation: any;
 

    basicOptions: any;
    DomesticCardsData:CardsData={
        TotalShipments: 0,
        Outbounds: 0,
        Inbounds: 0
    };
    IntAirCardsData:CardsData={
        TotalShipments: 0,
        Outbounds: 0,
        Inbounds: 0
    };
    IntRoadCardsData:CardsData={
        TotalShipments: 0,
        Outbounds: 0,
        Inbounds: 0
    };
    date!:Date;
    currentYear:Date=new Date();
    typesWithOutDuplicate!:(string | null | undefined)[]
   


    constructor(private dashboardService:DashboardService,
                private userService:UserService,
        ){}

    ngOnInit(): void {
        
     
      const documentStyle = getComputedStyle(document.documentElement);
        const textColor = documentStyle.getPropertyValue('--text-color');

        this.chartData = {
            labels: ['A', 'B', 'C'],
            datasets: [
                {
                    data: [540, 325, 702],
                    backgroundColor: [documentStyle.getPropertyValue('--blue-500'), documentStyle.getPropertyValue('--yellow-500'), documentStyle.getPropertyValue('--green-500')],
                    hoverBackgroundColor: [documentStyle.getPropertyValue('--blue-400'), documentStyle.getPropertyValue('--yellow-400'), documentStyle.getPropertyValue('--green-400')]
                }
            ]
        };

        this.chartOptions = {
            plugins: {
                legend: {
                    labels: {
                        usePointStyle: true,
                        color: textColor,
                    },
                    position:'bottom'
                }
            }
        };

        const documentStyleBar = getComputedStyle(document.documentElement);
        const textColorBar = documentStyleBar.getPropertyValue('--text-color');
        const textColorSecondaryBar = documentStyle.getPropertyValue('--text-color-secondary');
        const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    
        this.userService.getLoggedInUser().subscribe((res:User)=>{        
            let types=res.locations?.map((el)=>{return el.type})
           this.typesWithOutDuplicate= types!.filter((item, index) => types!.indexOf(item) === index); 
             this.getCardsData(this.currentYear)
              
        },(_error)=>{})
     
    }

    getCardsData(year:Date){
        // this.dashboardService.DomesticCardsData(year.getFullYear()).subscribe((res)=>{
        //    this.cardsData=res;
        // },(error)=>{})
        forkJoin([this.dashboardService.DomesticCardsData(year.getFullYear()),
            this.dashboardService.InternationalAirCardsData(year.getFullYear()),
            this.dashboardService.InternationalRoadCardsData(year.getFullYear()),
            this.dashboardService.lowAndHighVolumeByLocationOutboundDomestic(year.getFullYear()),
            this.dashboardService.lowAndHighVolumeByLocationInboundDomestic(year.getFullYear()),
            this.dashboardService.lowAndHighVolumeByLocationOutboundInternationalAir(year.getFullYear()),
            this.dashboardService.lowAndHighVolumeByLocationOutboundInternationalRoad(year.getFullYear()),
            this.dashboardService.lowAndHighVolumeByLocationInboundInternationalAir(year.getFullYear()),
            this.dashboardService.lowAndHighVolumeByLocationInboundInternationalRoad(year.getFullYear()),
            this.dashboardService.lowToHighDomesticOutboundTest(year.getFullYear()),
            this.dashboardService.lowToHighInternationalAirOutboundTest(year.getFullYear()),
            this.dashboardService.lowToHighInternationalRoadOutboundTest(year.getFullYear())
        ])
            .subscribe({next:([DomRes,IntAirRes,IntRoadRes,locationDomOutbound,locationDomInbound,locationIntAirOutbound,locationInRoadOutbound,locationIntAirInbound,locationInRoadInbound,DomesticOutboundLocationtoLocationData,IntAirOutboundLocationtoLocationData,IntRoadOutboundLocationtoLocationData])=>{
        this.DomesticCardsData=DomRes;
        this.IntAirCardsData=IntAirRes;
        this.IntRoadCardsData=IntRoadRes;
        this.DomOutLocationToLocation=Object.keys(DomesticOutboundLocationtoLocationData).map(el=>{
            return {[el]:{
                labels: Object.keys( DomesticOutboundLocationtoLocationData[el]) ,
                datasets: [
                    {
                        label: 'Outbound',
                        data: Object.values( DomesticOutboundLocationtoLocationData[el]),
                        backgroundColor: ['rgba(255, 159, 64)', 'rgba(75, 192, 192)'],
                        borderColor: ['rgb(255, 159, 64)', 'rgb(75, 192, 192)'],
                        borderWidth: 1,
                        pointStyle:'circle',
                        barPercentage: 10,
                        barThickness: 20,
                    }
                ]
            }  }
        })
        this.IntAirOutLocationToLocation=Object.keys(IntAirOutboundLocationtoLocationData).map(el=>{
            return {[el]:{
                labels: Object.keys( IntAirOutboundLocationtoLocationData[el]) ,
                datasets: [
                    {
                        label: 'Outbound',
                        data: Object.values( IntAirOutboundLocationtoLocationData[el]),
                        backgroundColor: ['rgba(255, 159, 64)', 'rgba(75, 192, 192)'],
                        borderColor: ['rgb(255, 159, 64)', 'rgb(75, 192, 192)'],
                        borderWidth: 1,
                        pointStyle:'circle',
                        barPercentage: 10,
                        barThickness: 20,
                    }
                ]
            }  }
        })
        this.IntRoadOutLocationToLocation=Object.keys(IntRoadOutboundLocationtoLocationData).map(el=>{
            return {[el]:{
                labels: Object.keys( IntRoadOutboundLocationtoLocationData[el]) ,
                datasets: [
                    {
                        label: 'Outbound',
                        data: Object.values( IntRoadOutboundLocationtoLocationData[el]),
                        backgroundColor: ['rgba(255, 159, 64)', 'rgba(75, 192, 192)'],
                        borderColor: ['rgb(255, 159, 64)', 'rgb(75, 192, 192)'],
                        borderWidth: 1,
                        pointStyle:'circle',
                        barPercentage: 10,
                        barThickness: 20,
                    }
                ]
            }  }
        })
        console.log(this.DomOutLocationToLocation);
        
               
        const documentStyle = getComputedStyle(document.documentElement);
        const textColor = documentStyle.getPropertyValue('--text-color');
        const documentStyleBar = getComputedStyle(document.documentElement);
        const textColorBar = documentStyleBar.getPropertyValue('--text-color');
        const textColorSecondaryBar = documentStyle.getPropertyValue('--text-color-secondary');
        const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

      
        this.locationDomOutData = {
            labels: Object.keys(locationDomOutbound) ,
            datasets: [
                {
                    label: 'Outbound',
                    data: Object.values(locationDomOutbound),
                    backgroundColor: ['rgba(255, 159, 64)', 'rgba(75, 192, 192)'],
                    borderColor: ['rgb(255, 159, 64)', 'rgb(75, 192, 192)'],
                    borderWidth: 1,
                    pointStyle:'circle',
                    barPercentage: 10,
                    barThickness: 20,
                }
            ]
        };
        this.locationDomInData = {
            labels: Object.keys(locationDomInbound) ,
            datasets: [
                {
                    label: 'Inbound',
                    data: Object.values(locationDomInbound),
                    backgroundColor: ['rgba(255, 159, 64)', 'rgba(75, 192, 192)'],
                    borderColor: ['rgb(255, 159, 64)', 'rgb(75, 192, 192)'],
                    borderWidth: 1,
                    pointStyle:'circle',
                    barPercentage: 10,
                    barThickness: 20,
                }
            ]
        };
        this.locationIntAirOutData = {
            labels: Object.keys(locationIntAirOutbound) ,
            datasets: [
                {
                    label: 'Outbound',
                    data: Object.values(locationIntAirOutbound),
                    backgroundColor: ['rgba(255, 159, 64)', 'rgba(75, 192, 192)'],
                    borderColor: ['rgb(255, 159, 64)', 'rgb(75, 192, 192)'],
                    borderWidth: 1,
                    pointStyle:'circle',
                    barPercentage: 10,
                    barThickness: 20,
                }
            ]
        };
        this.locationIntAirInData = {
            labels: Object.keys(locationIntAirInbound),
            datasets: [
                {
                    label: 'Inbound',
                    data: Object.values(locationInRoadOutbound),
                    backgroundColor: ['rgba(255, 159, 64)', 'rgba(75, 192, 192)'],
                    borderColor: ['rgb(255, 159, 64)', 'rgb(75, 192, 192)'],
                    borderWidth: 1,
                    pointStyle:'circle',
                    barPercentage: 10,
                    barThickness: 20,
                }
            ]
        };
        this.locationIntRoadOutData = {
            labels: Object.keys(locationInRoadOutbound) ,
            datasets: [
                {
                    label: 'Outbound',
                    data: Object.values(locationInRoadOutbound),
                    backgroundColor: ['rgba(255, 159, 64)', 'rgba(75, 192, 192)'],
                    borderColor: ['rgb(255, 159, 64)', 'rgb(75, 192, 192)'],
                    borderWidth: 1,
                    pointStyle:'circle',
                    barPercentage: 10,
                    barThickness: 20,
                }
            ]
        };
        this.locationIntRoadInData = {
            labels: Object.keys(locationInRoadInbound) ,
            datasets: [
                {
                    label: 'Inbound',
                    data: Object.values( locationInRoadInbound ),
                    backgroundColor: ['rgba(255, 159, 64)', 'rgba(75, 192, 192)'],
                    borderColor: ['rgb(255, 159, 64)', 'rgb(75, 192, 192)'],
                    borderWidth: 1,
                    pointStyle:'circle',
                    barPercentage: 10,
                    barThickness: 20,
                }
            ]
        };

        this.basicOptions = {
            plugins: {
                legend: {
                    labels: {
                        color: textColorBar,
                        usePointStyle: true,
                       
                    },
                    position:'bottom'
                }
            },
            scales: {
            
                y: {
                    beginAtZero: true,
                    ticks: {
                        color: textColorSecondaryBar
                    },
                    grid: {
                        color: surfaceBorder,
                        drawBorder: false
                    }
                },
                x: {
                  
                    ticks: {
                        color: textColorSecondaryBar
                    },
                    grid: {
                        color: surfaceBorder,
                        drawBorder: false
                    }
                }
            }
        };
   
      
    
        

        },error:(error)=>{}})
    }

    ifDomesticExist():boolean{
        return this.typesWithOutDuplicate.includes("Domestic")
    }
    ifInternationalAirExist():boolean{
        return this.typesWithOutDuplicate.includes("International Air")
    }
    ifInternationalRoadExist():boolean{
        return this.typesWithOutDuplicate.includes("International Road")
    }

    getFirstKey(obj: any): string {
        return Object.keys(obj)[0];
    }
 

}

