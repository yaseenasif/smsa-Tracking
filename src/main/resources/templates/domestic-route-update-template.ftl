<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>email</title>
    <style>
        #customers {
  font-family: Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

#customers td, #customers th {
  border: 1px solid #ddd;
  padding: 8px;
}

#customers tr:nth-child(even){background-color: #f2f2f2;}
    </style>
</head>
<body>
    <h2>Dear Concerned Team,</h2>

    <h4>Below route has been updated by Id number: ${field1}</h4>
    <h4>Old route details</h4>
    <table id="customers" style="width: 100%;">
        <tbody>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Id
                </td>
                <td style="text-align: center;" >
                    ${field1}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Origin
                </td>
                <td style="text-align: center;" >
                    ${field2}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Destination
                </td>
                <td style="text-align: center;" >
                    ${field3}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Route
                </td>
                <td style="text-align: center;" >
                    ${field4}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Driver
                </td>
                <td style="text-align: center;" >
                    ${field5}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Etd
                </td>
                <td style="text-align: center;" >
                    ${field6}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Eta
                </td>
                <td style="text-align: center;" >
                    ${field7}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Duration
                </td>
                <td style="text-align: center;" >
                    ${field8}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Remarks
                </td>
                <td style="text-align: center;" >
                    ${field9}
                </td>
            </tr>
        </tbody>
    </table>
    <h4>New route details</h4>
    <table id="customers" style="width: 100%;">
        <tbody>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Id
                </td>
                <td style="text-align: center;" >
                    ${field1}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Origin
                </td>
                <td style="text-align: center;" >
                    ${field10}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Destination
                </td>
                <td style="text-align: center;" >
                    ${field11}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Route
                </td>
                <td style="text-align: center;" >
                    ${field12}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Driver
                </td>
                <td style="text-align: center;" >
                    ${field13}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Etd
                </td>
                <td style="text-align: center;" >
                    ${field14}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Eta
                </td>
                <td style="text-align: center;" >
                    ${field15}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Duration
                </td>
                <td style="text-align: center;" >
                    ${field16}
                </td>
            </tr>
            <tr>
                <td style="background-color:#420097;color:white;font-weight: bolder;">
                    Remarks
                </td>
                <td style="text-align: center;" >
                    ${field17}
                </td>
            </tr>
        </tbody>
    </table>
</body>
</html>