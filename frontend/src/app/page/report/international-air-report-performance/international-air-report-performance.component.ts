import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { InternationalAirReportPerformance } from 'src/app/model/InternationalAirReportPerformance';
import { ReportService } from '../report.service';
import { ProductField } from 'src/app/model/ProductField';
import { ProductFieldServiceService } from '../../product-field/service/product-field-service.service';
import { DatePipe } from '@angular/common';
import * as XLSX from 'xlsx';
import { Workbook } from 'exceljs';

@Component({
  selector: 'app-international-air-report-performance',
  templateUrl: './international-air-report-performance.component.html',
  styleUrls: ['./international-air-report-performance.component.scss'],
  providers:[MessageService,DatePipe]
})
export class InternationalAirReportPerformanceComponent {
  value!:any

  items: MenuItem[] | undefined;

  searchBy:any={
    fromDate: '',
    toDate: '',
    status: '',
    origin: '',
    destination: [],
    type: '',
    routeNumber: ''
  }
  shipmentStatus!:ProductField|null;

  internationalAirReportPerformance!:InternationalAirReportPerformance[];
  constructor(private messageService:MessageService,private reportService:ReportService,private productFieldServiceService:ProductFieldServiceService,private datePipe:DatePipe){}




  ngOnInit() {
      this.items = [{ label: 'Reports',routerLink:'/report-tiles'},{ label: 'International Shipment By Air Report Of Performance'}];
      this.getAllShipmentStatus();
      this.getInternationalAirReportPerformance(this.searchBy);
    }

    getInternationalAirReportPerformance(searchBy:SearchBy){
      this.reportService.getInternationalAirReportPerformance(searchBy).subscribe((res:InternationalAirReportPerformance[])=>{
        this.internationalAirReportPerformance=res;
          },(error)=>{
            this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
          })
    }
    getAllShipmentStatus() {
      this.productFieldServiceService.getProductFieldByName("Search_For_International_By_Air").subscribe((res: ProductField) => {
        this.shipmentStatus = res;
      }, (error) => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      })
    }
    searchByFilter(){
      this.internationalAirReportPerformance=[]
      this.searchBy.fromDate=this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd'))!:"";
      this.searchBy.toDate=this.datePipe.transform(this.searchBy.toDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.toDate, 'yyyy-MM-dd'))!:"";
      this.reportService.getInternationalAirReportPerformance(this.searchBy).subscribe((res:InternationalAirReportPerformance[])=>{
        this.internationalAirReportPerformance=res;
        this.searchBy.fromDate= this.searchBy.fromDate ? new Date( this.searchBy.fromDate) : "";
        this.searchBy.toDate= this.searchBy.toDate ? new Date( this.searchBy.toDate) : "";
          },(error)=>{
            this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
            this.searchBy.fromDate= this.searchBy.fromDate ? new Date( this.searchBy.fromDate) : "";
            this.searchBy.toDate= this.searchBy.toDate ? new Date( this.searchBy.toDate) : "";
          })
    }

    clearFilter(){
      this.searchBy={
        fromDate: '',
        toDate: '',
        status: '',
        origin: '',
        destination: [],
        type: '',
        routeNumber: ''
      }
    }

    downloadFile() {
      this.searchBy.fromDate=this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd'))!:"";
      this.searchBy.toDate=this.datePipe.transform(this.searchBy.toDate, 'yyyy-MM-dd')!=null?(this.datePipe.transform(this.searchBy.fromDate, 'yyyy-MM-dd'))!:"";
      this.reportService.downloadReportExcel("/int-air-rep-per",this.searchBy,"International Air Report Performance.xlsx");
    }

    // exportToExcel(): void {
    //   // Excel Title, Header
    //   const title = 'International Air Report Performance';
    //   const headers = ['Id', 'PreAlert Number', 'Reference Number', 'Origin', 'Destination', 'Route', 'Flight', 'Actual Time Departure', 'Actual Time Arrival', 'Cleared', 'Total Transit Time', 'Total Lead Time'];

    //   // Create workbook and worksheet
    //   const workbook = new Workbook();
    //   const worksheet = workbook.addWorksheet('data');

    //   // Add Row and formatting for title
    //   const titleRow = worksheet.addRow([title]);
    //   titleRow.font = { name: 'Corbel', family: 4, size: 16, underline: 'double', bold: true };
    //   worksheet.addRow([]);

    //   // Add Header Row
    //   const headerRow = worksheet.addRow(headers);

    //   // Cell Style : Fill and Border for header
    //   headerRow.eachCell((cell: any, number: number) => {
    //     cell.fill = {
    //       type: 'pattern',
    //       pattern: 'solid',
    //       fgColor: { argb: 'FFFFFF00' },
    //       bgColor: { argb: 'FF0000FF' }
    //     };
    //     cell.border = { top: { style: 'thin' }, left: { style: 'thin' }, bottom: { style: 'thin' }, right: { style: 'thin' } };
    //   });

    //   // Add Data
    //   this.internationalAirReportPerformance.forEach(item => {
    //     const rowData = Object.values(item);
    //     worksheet.addRow(rowData);
    //   });

    //   // Generate Excel File with given name
    //   workbook.xlsx.writeBuffer().then((data: any) => {
    //     const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    //     this.saveAsExcelFile(blob, 'International_Air_Report_Performance');
    //   });
    // }

    // private saveAsExcelFile(buffer: any, fileName: string): void {
    //   const downloadLink = document.createElement('a');
    //   downloadLink.href = window.URL.createObjectURL(buffer);
    //   downloadLink.download = `${fileName}.xlsx`;
    //   document.body.appendChild(downloadLink);
    //   downloadLink.click();
    //   document.body.removeChild(downloadLink);
    // }


}

interface SearchBy{
  fromDate:string|Date,
  toDate:string|Date,
  status:string,
  origin:string,
  destination:string,
  type:string,
  routeNumber:string
}
